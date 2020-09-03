package com.zjx.thread.sync;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * synchronized 锁变了
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/8/31 14:43
 * @Version V1.0
 **/
public class Sync8 {

    @Test
    public void testSync() {

        CountDownLatch latch = new CountDownLatch(2);

        new Thread(() -> {
            print();
            latch.countDown();
        }).start();

        new Thread(() -> {
            print();
            latch.countDown();
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private String lock = "123";

    private void print() {
        synchronized (lock){
            System.out.println("lock print:" + Thread.currentThread().getName());
            try {
                lock = "456";
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("release print:" + Thread.currentThread().getName());
        }
    }

}
