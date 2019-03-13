package com.zjx.thread.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Description CountDownLatch允许一个或多个线程等待其他线程完成操作
 *
 * CountDownLatch也可以实现join的功能
 *
 * CountDownLatch的计数器只能使用一次，而CyclicBarrier的计数器可以使用reset()方法重置。所以CyclicBarrier能处理更为复杂的业务场景。
 *
 * @Author Carson Cheng
 * @Date 2019/3/13 17:38
 * @Version V1.0
 **/
public class CountDownLatchTest {

    // CountDownLatch的构造函数接收一个int类型的参数作为计数器，如果你想等待N个点完成，这里就传入N
    static CountDownLatch c = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
                c.countDown(); // 计数器减1
                System.out.println(2);
                c.countDown(); // 计数器减1
            }
        }).start();

        // 计数器必须大于等于0，只是等于0时候，计数器就是零，调用await方法时不会阻塞当前线程。
        // 一个线程调用countDown方法happen-before，另外一个线程调用await方法。
        c.await(); // 阻塞当前线程，直到N变成零
        c.await(3, TimeUnit.SECONDS); // 阻塞3秒
        System.out.println("3");
    }
}