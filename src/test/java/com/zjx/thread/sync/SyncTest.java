package com.zjx.thread.sync;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2020/8/31 10:22
 * @Version V1.0
 **/
public class SyncTest {

    @Test
    public void testSync1() {
        int nThread = 2;
        CountDownLatch latch = new CountDownLatch(nThread);
//        for (int i = 0; i < nThread; i++) {
//            new Thread(() -> {
//                // 示例1
//                //doSomething();
//                // 示例2
//                //doSomethingA();
//                //doSomethingB();
//                // 示例3
//                doAnything("admin", "123456");
//                latch.countDown();
//            }).start();
//        }

        new Thread(() -> {
            // 示例4
            //doSomethingA();
            // 示例5
            add();
            latch.countDown();
        }).start();
        new Thread(() -> {
            // 示例4
            //doSomethingB();
            // 示例5
            add();
            latch.countDown();
        }).start();

        try {
            latch.await();
            Thread.sleep(1000);
            System.out.println("getSize:" + getSize());
            System.out.println(Thread.currentThread().getName() + "执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void doSomething() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + ", i=" + i);
        }
        synchronized (this) {
            for (int j = 0; j < 100; j++) {
                System.out.println(Thread.currentThread().getName() + ", j=" + j);
            }
        }
    }

    private void doSomethingA() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + ", A begin time:" + System.currentTimeMillis());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ", A end time:" + System.currentTimeMillis());
        }
    }

    private void doSomethingB() {
        String other = new String();
        //synchronized (this) {
        synchronized (other) {
            System.out.println(Thread.currentThread().getName() + ", B begin time:" + System.currentTimeMillis());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ", B end time:" + System.currentTimeMillis());
        }
    }


    private String username;
    private String password;
    private void doAnything(String username, String password) {
        try {
            String anything = new String();
            synchronized (anything) {
                System.out.println(Thread.currentThread().getName() + ", entry!!!");
                this.username = username;
                Thread.sleep(3000);
                this.password = password;
                System.out.println(Thread.currentThread().getName() + ", leave!!!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<String> list = new ArrayList<>();
    private Integer count = 0;
    private void add(){
        System.out.println(Thread.currentThread().getName() + ", add start!!!");
        synchronized(list) {
            if (list.size() < 1) {
                try {
                    Thread.sleep(1000);
                    list.add("zzz");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("添加：" + ++count);
        System.out.println(Thread.currentThread().getName() + ", add end!!!");
    }
    private synchronized int getSize(){
        System.out.println(Thread.currentThread().getName() + ", getSize entry!!!");
        final int size = list.size();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("次数:" + count);
        System.out.println(Thread.currentThread().getName() + ", getSize leave!!!");
        return size;
    }

}
