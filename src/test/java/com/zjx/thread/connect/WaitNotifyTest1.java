package com.zjx.thread.connect;

import java.util.concurrent.CountDownLatch;

/**
 * wait() 释放锁
 * sleep() 不释放锁
 * 执行完notify()所在的同步方法或代码块才释放锁
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/1 9:57
 * @Version V1.0
 **/
public class WaitNotifyTest1 {

    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(2);
        new Thread(() -> {
            waiting();
            latch.countDown();
        }).start();
        new Thread(() -> {
            //notifyting();
            waiting();
            latch.countDown();
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Object object = new Object();

    private static void notifyting() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (object) {
            System.out.println("start notifyting...");
            object.notify();
            System.out.println("end notifyting...");
        }
    }

    private static void waiting() {
        try {
            synchronized (object) {
                System.out.println("start waiting...");
                //object.wait();
                //object.wait(3000); // 等待3秒自动通知
                Thread.sleep(5000);
                System.out.println("end waiting...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
