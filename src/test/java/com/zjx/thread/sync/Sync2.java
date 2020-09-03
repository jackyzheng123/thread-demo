package com.zjx.thread.sync;

import org.junit.Test;

/**
 * synchronized 修饰 非static  对象锁
 * synchronized 修饰 static  类锁
 *
 * 异步执行
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/8/31 14:34
 * @Version V1.0
 **/
public class Sync2 {

    @Test
    public void testSync (){

        new Thread(()->{
            printA();
        }).start();

//        new Thread(()->{
//            printB();
//        }).start();

        new Thread(()->{
            printC();
        }).start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * synchronized 修饰 非static  对象锁
     */
    private synchronized void printC() {
        System.out.println("lock printC:" + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("release printC:" + Thread.currentThread().getName());
    }

    /**
     * synchronized 修饰 static  类锁
     */
    private synchronized static void printB() {
        System.out.println("lock printB:" + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("release printB:" + Thread.currentThread().getName());
    }

    /**
     * synchronized 修饰 static  类锁
     */
    private synchronized static void printA() {
        System.out.println("lock printA:" + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("release printA:" + Thread.currentThread().getName());
    }
}
