package com.zjx.thread.sync;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * synchronized 一线程无限循环， 导致其他线程获取不到对象锁，永远等待
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/8/31 14:43
 * @Version V1.0
 **/
public class Sync6 {

    @Test
    public void testSync() {

        CountDownLatch latch = new CountDownLatch(2);

        new Thread(() -> {
            printA();
            latch.countDown();
        }).start();

        new Thread(() -> {
            printB();
            latch.countDown();
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 同步方法
//    private synchronized void printA() {
//        System.out.println("lock printA:" + Thread.currentThread().getName());
//        try {
//            while (true) {
//                System.out.println("lock printA...");
//                Thread.sleep(1000);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("release printA:" + Thread.currentThread().getName());
//    }
//
//    private synchronized void printB() {
//        System.out.println("lock printB:" + Thread.currentThread().getName());
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("release printB:" + Thread.currentThread().getName());
//    }


    // 同步代码块
    private Object obj1 = new Object();
    private Object obj2 = new Object();

    private void printA() {
        synchronized (obj1){
            System.out.println("lock printA:" + Thread.currentThread().getName());
            try {
                while (true) {
                    System.out.println("lock printA...");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("release printA:" + Thread.currentThread().getName());
        }
    }

    private void printB() {
        synchronized (obj2){
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
