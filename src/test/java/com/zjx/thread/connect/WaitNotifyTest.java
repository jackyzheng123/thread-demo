package com.zjx.thread.connect;

import java.util.concurrent.CountDownLatch;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/1 9:57
 * @Version V1.0
 **/
public class WaitNotifyTest {

    public static void main(String[] args) {
        //testWait();

        CountDownLatch latch = new CountDownLatch(2);
        new Thread(() -> {
            waiting();
            latch.countDown();
        }).start();
        new Thread(() -> {
            notifyting();
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
                object.wait();
                System.out.println("end waiting...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void testWait() {
        try {
            Object obj = new Object();
            System.out.println("entry sync...");
            synchronized (obj) {
                System.out.println("lock obj");
                System.out.println("waiting...");
                obj.wait();
                System.out.println("waited...");
            }
            System.out.println("exit sync...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
