package com.zjx.thread.aspect;

import com.google.common.collect.ImmutableList;
import com.zjx.thread.annotation.RedisLimit;
import com.zjx.thread.enums.LimitType;
import com.zjx.thread.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @Description Redis分布式限流切面
 * @Author Carson Cheng
 * @Date 2020/5/14 9:40
 * @Version V1.0
 **/
@Component
@Aspect
@Slf4j
public class RedisLimitAspect {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Pointcut("execution(public * *(..)) && @annotation(com.zjx.thread.annotation.RedisLimit)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object interceptor(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedisLimit limitAnno = method.getAnnotation(RedisLimit.class);
        LimitType limitType = limitAnno.limitType();
        String name = limitAnno.name();

        String key = null;
        int limitPeriod = limitAnno.period();
        int limitCount = limitAnno.count();
        switch (limitType) {
            case IP:
                key = IPUtils.getIpAddress();
                break;
            case CUSTOMER:
                // TODO 如果此处想根据表达式或者一些规则生成 请看 一起来学Spring Boot | 第二十三篇：轻松搞定重复提交（分布式锁）
                key = limitAnno.key();
                break;
            default:
                break;
        }

        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(limitAnno.prefix(), key));
        try {
            RedisScript<Number> redisScript = new DefaultRedisScript<Number>(buildLuaScript2(), Number.class);
            Number count = redisTemplate.execute(redisScript, keys, limitCount, limitPeriod);
            log.info("Access try count is {} for name={} and key = {}", count, name, key);

            //if (count != null && count.intValue() <= limitCount) {
            if (count != null && count.intValue() == 1) {
                return joinPoint.proceed();
            } else {
                throw new RuntimeException("You have been dragged into the blacklist");
            }
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
            throw new RuntimeException("server exception");
        }
    }

    /**
     * 限流 脚本（计数器方式）
     *
     * @return lua脚本
     */
    private String buildLuaScript() {
        StringBuilder lua = new StringBuilder();
        lua.append("local c")
                .append("\nc = redis.call('get', KEYS[1])")
                // 调用不超过最大值，则直接返回
                .append("\nif c and tonumber(c) > tonumber(ARGV[1]) then")
                .append("\nreturn c;")
                .append("\nend")
                // 执行计算器自加
                .append("\nc = redis.call('incr', KEYS[1])")
                .append("\nif tonumber(c) == 1 then")
                // 从第一次调用开始限流，设置对应键值的过期
                .append("\nredis.call('expire', KEYS[1], ARGV[2])")
                .append("\nend")
                .append("\nreturn c;");
        return lua.toString();
    }

    /**
     * 限流 脚本（处理临界时间大量请求的情况）
     *
     * @return lua脚本
     */
    private String buildLuaScript2() {
        StringBuilder lua = new StringBuilder();
        lua.append("redis.replicate_commands(); local listLen, time")
                .append("\nlistLen = redis.call('LLEN', KEYS[1])")
                // 不超过最大值，则直接写入时间
                .append("\nif listLen and tonumber(listLen) < tonumber(ARGV[1]) then")
                .append("\nlocal a = redis.call('TIME');")
                .append("\nredis.call('LPUSH', KEYS[1], a[1]*1000000+a[2])")
                .append("\nelse")
                // 取出现存的最早的那个时间，和当前时间比较，看是小于时间间隔
                .append("\ntime = redis.call('LINDEX', KEYS[1], -1)")
                .append("\nlocal a = redis.call('TIME');")
                .append("\nif a[1]*1000000+a[2] - time < tonumber(ARGV[2])*1000000 then")
                // 访问频率超过了限制，返回0表示失败
                .append("\nreturn 0;")
                .append("\nelse")
                .append("\nredis.call('LPUSH', KEYS[1], a[1]*1000000+a[2])")
                .append("\nredis.call('LTRIM', KEYS[1], 0, tonumber(ARGV[1])-1)")
                .append("\nend")
                .append("\nend")
                .append("\nreturn 1;");
        return lua.toString();
    }

}