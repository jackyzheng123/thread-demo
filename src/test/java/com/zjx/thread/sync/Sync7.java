package com.zjx.thread.sync;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * synchronized 死锁
 *
 *
 * 命令jps
 *
 * jstack -l pid 查看线程锁的信息
 *
 * Java stack information for the threads listed above:
 * ===================================================
 * "Thread-1":
 *         at com.zjx.thread.sync.Sync7.printB(Sync7.java:66)
 *         - waiting to lock <0x00000000d6503190> (a java.lang.Object)
 *         - locked <0x00000000d65031a0> (a java.lang.Object)
 *         at com.zjx.thread.sync.Sync7.lambda$testSync$1(Sync7.java:29)
 *         at com.zjx.thread.sync.Sync7$$Lambda$2/1355531311.run(Unknown Source)
 *         at java.lang.Thread.run(Thread.java:748)
 * "Thread-0":
 *         at com.zjx.thread.sync.Sync7.printA(Sync7.java:54)
 *         - waiting to lock <0x00000000d65031a0> (a java.lang.Object)
 *         - locked <0x00000000d6503190> (a java.lang.Object)
 *         at com.zjx.thread.sync.Sync7.lambda$testSync$0(Sync7.java:24)
 *         at com.zjx.thread.sync.Sync7$$Lambda$1/1595212853.run(Unknown Source)
 *         at java.lang.Thread.run(Thread.java:748)
 *
 * Found 1 deadlock.
 *
 *
 *
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/8/31 14:43
 * @Version V1.0
 **/
public class Sync7 {


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

    // 同步代码块
    private Object obj1 = new Object();
    private Object obj2 = new Object();

    private void printA() {
        synchronized (obj1){
            System.out.println("lock obj1:" + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (obj2){
                System.out.println("lock obj2:" + Thread.currentThread().getName());
                System.out.println("release obj2:" + Thread.currentThread().getName());
            }

            System.out.println("release obj1:" + Thread.currentThread().getName());
        }
    }

    private void printB() {
        synchronized (obj2){
            System.out.println("lock obj2:" + Thread.currentThread().getName());
            synchronized (obj1){
                System.out.println("lock obj1:" + Thread.currentThread().getName());
                System.out.println("release obj1:" + Thread.currentThread().getName());
            }
            System.out.println("release obj2:" + Thread.currentThread().getName());
        }
    }


}
