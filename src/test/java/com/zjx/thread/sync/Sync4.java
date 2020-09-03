package com.zjx.thread.sync;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * synchronized (object) 和 synchronized (this) 异步执行， 不互斥
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/8/31 14:43
 * @Version V1.0
 **/
public class Sync4 {

    @Test
    public void testSync (){

        CountDownLatch latch = new CountDownLatch(2);

        new Thread(()->{
            printB();
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

    private Object object = new Object();
    private synchronized void printA() {
        System.out.println("lock printA:" + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("release printA:" + Thread.currentThread().getName());
    }

    private void printB() {
        synchronized (object) {
            System.out.println("lock printB:" + Thread.currentThread().getName());
            try {
                printA();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("release printB:" + Thread.currentThread().getName());
        }
    }


}
