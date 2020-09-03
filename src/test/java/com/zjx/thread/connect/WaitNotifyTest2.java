package com.zjx.thread.connect;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * notify()只通知一次
 * notifyAll() 通知所有
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/1 11:09
 * @Version V1.0
 **/
public class WaitNotifyTest2 {

    @Test
    public void test (){
        int nThread = 4;
        CountDownLatch latch = new CountDownLatch(nThread);
        for (int i = 0; i < 3; i++) {
            new Thread(() ->{
                waiting();
                latch.countDown();
            }).start();
        }

        // 通知
        new Thread(() ->{
            notifying();
            latch.countDown();
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Object lock = new Object();

    private void waiting() {
        synchronized (lock){
            System.out.println("start waiting...");
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end waiting...");
        }
    }

    private void notifying() {
        synchronized (lock){
            System.out.println("start notifying...");
            //lock.notify();
            lock.notifyAll();
            System.out.println("end notifying...");
        }
    }
}
