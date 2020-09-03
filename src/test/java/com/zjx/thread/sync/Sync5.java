package com.zjx.thread.sync;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 *
 * synchronized (class) 类锁，与synchronized 方法加static 类锁
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/8/31 14:43
 * @Version V1.0
 **/
public class Sync5 {

    @Test
    public void testSync (){

        CountDownLatch latch = new CountDownLatch(2);

        new Thread(()->{
            printA();
            latch.countDown();
        }).start();

        new Thread(()->{
            printB();
            latch.countDown();
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized static void printA() {
        System.out.println("lock printA:" + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("release printA:" + Thread.currentThread().getName());
    }

    private void printB() {
        synchronized (Sync5.class) {
            System.out.println("lock printB:" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("release printB:" + Thread.currentThread().getName());
        }
    }


}
