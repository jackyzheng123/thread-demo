package com.zjx.ratelimit;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 限流算法：
 * 令牌桶: 能够限制数据的平均传输速率外，还允许某种程度的突发传输
 * 漏桶: 水（请求）先进入到漏桶里，漏桶以一定的速度出水，当水流入速度过大会直接溢出，可以看出漏桶算法能强行限制数据的传输速率。
 *
 * @Description 限流测试
 * @Author Carson Cheng
 * @Date 2020/3/17 15:00
 * @Version V1.0
 **/
public class RateLimitTest {

    /**
     * 限流总并发/连接/请求
     * TPS/QPS
     * 如Tomcat的Connector配置参数：acceptCount/maxConnections/maxThreads
     */

    /**
     * 限流总资源数：稀缺资源 连接池、线程池
     */

    /**
     * 限流某个接口总并发/请求数
     */
    @Test
    public void test0() {
        AtomicInteger atomic = new AtomicInteger(0);
        int limit = 1000;

        while (true) {
            try {
                if (atomic.incrementAndGet() > limit) {
                    System.out.println("拒绝请求");
                    return;
                }
                System.out.println("处理请求：" + atomic.get());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 限流某个接口的时间窗请求数
     */
    @Test
    public void test() throws ExecutionException {

        LoadingCache<Long, AtomicLong> counter = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build(new CacheLoader<Long, AtomicLong>() {
                    @Override
                    public AtomicLong load(Long key) throws Exception {
                        return new AtomicLong(0);
                    }
                });

        long limit = 1000;
        AtomicInteger num = new AtomicInteger(0);
        while (true) {
            long currentSeconds = System.currentTimeMillis() / 1000;
            if (counter.get(currentSeconds).incrementAndGet() > limit) {
                System.out.println("限流了：" + currentSeconds);
                continue;
            } else {
                System.out.println("业务处理：" + num.incrementAndGet());
            }
        }
    }

    /**
     * Guava RateLimiter 令牌桶算法，用于平滑突发限流SmoothBursty、平滑预热限流SmoothWarmingUp
     * <p>
     * 将突发请求速率平均为固定请求速率
     */
    @Test
    public void test1() {
        // 创建一个桶，桶容量为5，每秒新增5个令牌， 即每隔200毫秒新增一个令牌
        RateLimiter rateLimiter = RateLimiter.create(5);

        // acquire()消费1个令牌， 如果有足够的令牌，则成功返回0； 如果没有令牌，则暂停一段时间。
        // 发令牌间隔是200毫秒，则等待200毫秒再去消费令牌
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());

    }

    @Test
    public void test2() {
        // 创建一个桶，桶容量为5，每秒新增5个令牌， 即每隔200毫秒新增一个令牌
        RateLimiter limiter = RateLimiter.create(5);

        // acquire(10)消费10个令牌
        System.out.println(limiter.acquire(10));
        System.out.println(limiter.acquire(1)); // 突发，等2秒才有令牌
        System.out.println(limiter.acquire(1)); // 之后固定速率200毫秒
        System.out.println(limiter.acquire(1));
    }


    @Test
    public void test3() throws InterruptedException {
        // 创建一个桶，桶容量为2，每秒新增2个令牌， 即每隔500毫秒新增一个令牌

        // 桶容量为2，即允许突发量2，最大突发秒数默认值为1秒，  突发量/桶容量=速率
        RateLimiter limiter = RateLimiter.create(2);
        System.out.println(limiter.acquire());
        Thread.sleep(2000L);
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());

        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        // tryAcquire()无阻塞或可超时的令牌消费
        System.out.println(limiter.tryAcquire());
    }


    /**
     * 平滑预热限流SmoothWarmingUp
     * 速率是梯形的上升速率
     */
    @Test
    public void test4() throws InterruptedException {
        final RateLimiter rateLimiter = RateLimiter.create(5, 1000, TimeUnit.MILLISECONDS);

        for (int i = 0; i < 5; i++) {
            System.out.println(rateLimiter.acquire());
        }

        Thread.sleep(1000L);

        for (int i = 0; i < 5; i++) {
            System.out.println(rateLimiter.acquire());
        }

    }

}
