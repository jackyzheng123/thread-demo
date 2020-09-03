package com.zjx.thread.sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 对象、变量并发访问
 * @Author Carson Cheng
 * @Date 2020/8/28 16:06
 * @Version V1.0
 **/
public class ConCurrentTest {

    public static void main(String[] args) {
        int nThread = 10;
        CountDownLatch latch = new CountDownLatch(nThread);
        for (int i = 0; i < nThread; i++) {
            new Thread(() -> {
                setValue(100000);
                latch.countDown();
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static Integer num = 1;
    static AtomicInteger a = new AtomicInteger(1);

    private synchronized static void setValue(int count) {
        //int num = 1;
        for (int i = 0; i < count; i++) {
            System.out.println(Thread.currentThread().getName() + " : " + num++);
            System.out.println("a:" + a.getAndIncrement());
        }
    }


}
