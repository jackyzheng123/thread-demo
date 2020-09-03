package com.zjx.thread.vola;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile非原子性
 * @Description
 * @Author Carson Cheng
 * @Date 2020/8/31 17:23
 * @Version V1.0
 **/
public class VolatileTest2 {

    private volatile Integer count = 0;
    // 原子类
    private AtomicInteger atomic = new AtomicInteger(0);
    // synchronized加内置锁
    private Integer syncInt = 0;

    @Test
    public void test() {
        int nThread = 10;
        CountDownLatch latch = new CountDownLatch(nThread);
        for (int i = 0; i < nThread; i++) {
            new Thread(()->{
                add();
                latch.countDown();
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void add() {
        for (int i = 0; i < 10000; i++) {
            System.out.println("volatile：" + (++count));
            System.out.println("atomic：" + atomic.incrementAndGet());

            synchronized (syncInt){
                System.out.println("synchronized：" + (++syncInt));
            }
        }
    }
}
