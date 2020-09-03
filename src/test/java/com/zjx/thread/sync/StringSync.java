package com.zjx.thread.sync;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * 字符串常量池的死锁
 * <p>
 * String不能作为锁对象的原因： 字符串常量池缓存字符串，持有相同一把的锁
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/8/31 15:40
 * @Version V1.0
 **/
public class StringSync {

    @Test
    public void testStringSync() {
        CountDownLatch latch = new CountDownLatch(2);

        new Thread(() -> {
            //print("AA");
            print(new Object());
            latch.countDown();
        }).start();

        new Thread(() -> {
            //print("AA");
            print(new Object());
            latch.countDown();
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void print(Object param) {
        synchronized (param) {
            while (true) {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
