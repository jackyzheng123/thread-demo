package com.zjx.thread.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.zjx.thread.annotation.RateLimit;
import com.zjx.thread.annotation.RedisLimit;
import com.zjx.thread.entity.ResultJson;
import com.zjx.thread.enums.LimitType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2020/5/13 15:11
 * @Version V1.0
 **/
@RestController
@Slf4j
public class LimitTestController {

    // 每秒产生10个令牌
    RateLimiter rateLimiter = RateLimiter.create(10);

    AtomicInteger num = new AtomicInteger(100);

    @GetMapping("/test")
    public String test() {
        // 消费一个令牌， 初始化10个的容量，所以前10个请求无需等待直接成功，后面的开始被1秒10次限流了，基本上每0.1秒放行一个。
        System.out.println("等待时间:" + rateLimiter.acquire());
        System.out.println(Thread.currentThread().getName());

        return "hello";
    }

    /**
     * tryAcquire(long timeout, TimeUnit unit)
     * 从RateLimiter 获取许可如果该许可可以在不超过timeout的时间内获取得到的话，
     * 或者如果无法在timeout 过期之前获取得到许可的话，那么立即返回false（无需等待）
     */
    @PutMapping("/buy")
    public Object miao() {
        //判断能否在1秒内得到令牌，如果不能则立即返回false，不会阻塞程序
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            System.out.println("短期无法获取令牌，真不幸，排队也瞎排");
            return "失败";
        }
        if (num.decrementAndGet() > 0) {
            System.out.println("购买成功");
            return "成功";
        }
        System.out.println("库存不足，失败");
        return "失败";
    }

    @GetMapping("/getResults")
    @RateLimit(limitNum = 5)
    public ResultJson getResults() {
        log.info("调用了方法getResults");
        return ResultJson.ok(200, "成功");
    }

    @GetMapping("/getResultTwo")
    @RateLimit(limitNum = 10)
    public ResultJson getResultTwo() {
        log.info("调用了方法getResultTwo");
        return ResultJson.ok(200, "成功");
    }


    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    /**
     * Redis分布式限流测试
     *
     * @return
     */
    @RedisLimit(key = "limitTest", period = 100, count = 10, limitType = LimitType.IP) // 意味著 100S 内最多允許訪問10次
    @GetMapping("/limitTest")
    public int testLimiter() {
        return ATOMIC_INTEGER.incrementAndGet();
    }
}
