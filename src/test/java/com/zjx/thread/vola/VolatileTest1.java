package com.zjx.thread.vola;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * volatile 线程之间可见
 *
 * @Description volatile 测试
 * @Author Carson Cheng
 * @Date 2020/8/31 16:41
 * @Version V1.0
 **/
public class VolatileTest1 {

    private volatile boolean flag = true;

    @Test
    public void test() {
        CountDownLatch latch = new CountDownLatch(2);

        new Thread(()->{
            print();
            latch.countDown();
        }).start();

        new Thread(()->{
            stop();
            latch.countDown();
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("3秒停止打印:" + Thread.currentThread().getName());
        flag = false;
    }

    private void print() {
        try {
            while (flag) {
                System.out.println(Thread.currentThread().getName() + " print...");
                Thread.sleep(100);
            }
            System.out.println("print stop!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
