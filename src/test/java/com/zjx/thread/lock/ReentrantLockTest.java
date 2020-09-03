package com.zjx.thread.lock;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock
 * int getHoldCount() 返回调用lock()的次数
 * int getQueueLength() 返回等待获取锁的个数： 比如5个线程中，有一个线程调用了await(), 说明有四个同时等待锁释放
 * int getWaitQueueLength(condition) 返回等待与此锁定相关条件condition的线程估计数
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/1 16:48
 * @Version V1.0
 **/
public class ReentrantLockTest {

    private ReentrantLock lock = new ReentrantLock(false);
    private Integer count = 0;

    @Test
    public void testFirst() {
        int nThread = 10;
        CountDownLatch latch = new CountDownLatch(nThread);
        for (int i = 0; i < nThread; i++) {
            new Thread(() -> {
                test();
                latch.countDown();
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void test() {
        try {
            lock.lock();
            for (int i = 0; i < 10000; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + ++count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
