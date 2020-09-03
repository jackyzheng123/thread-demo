package com.zjx.thread.connect;

import java.util.concurrent.CountDownLatch;

/**
 * wait()
 * interrupt()
 *
 * 当线程wait()状态，调用线程对象的interrupt()会抛出InterruptedException异常
 *
 * 1. 执行完同步代码块就会释放对象锁
 * 2. 在执行同步代码块过程中，如果遇到异常导致线程终止，锁也会被释放
 * 3. 执行同步代码块中，sleep()不会释放对象锁，调用wait()会释放对象锁，等待被通知notify()、notifyAll()
 *
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/1 10:56
 * @Version V1.0
 **/
public class WaitInterruptTest {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            testMethod();
        });
        t1.start();

        try {
            Thread.sleep(5000);
            t1.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Object object = new Object();

    private static void testMethod() {
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

}
