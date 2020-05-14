package com.zjx.thread.aspect;

import com.zjx.thread.annotation.CacheLock;
import com.zjx.thread.service.CacheKeyGeneratorService;
import com.zjx.thread.utils.RedisLockHelper;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2020/5/14 11:31
 * @Version V1.0
 **/
@Component
@Aspect
public class LockMethodAspect {

    private final RedisLockHelper redisLockHelper;

    private final CacheKeyGeneratorService cacheKeyGenerator;

    @Autowired
    public LockMethodAspect(RedisLockHelper redisLockHelper, CacheKeyGeneratorService cacheKeyGenerator) {
        this.redisLockHelper = redisLockHelper;
        this.cacheKeyGenerator = cacheKeyGenerator;
    }

    @Pointcut("execution(public * *(..)) && @annotation(com.zjx.thread.annotation.CacheLock)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object interceptor(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CacheLock lock = method.getAnnotation(CacheLock.class);
        if (StringUtils.isEmpty(lock.prefix())) {
            throw new RuntimeException("lock key don't null...");
        }
        final String lockKey = cacheKeyGenerator.getLockKey(joinPoint);
        String value = UUID.randomUUID().toString();
        try {
            // 假设上锁成功，但是设置过期时间失效，以后拿到的都是 false
            final boolean success = redisLockHelper.lock(lockKey, value, lock.expire(), lock.timeUnit());
            if (!success) {
                throw new RuntimeException("重复提交");
            }
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException("系统异常");
            }
        } finally {
            // TODO 如果演示的话需要注释该代码;实际应该放开
            // redisLockHelper.unlock(lockKey, value);
        }
    }
}
