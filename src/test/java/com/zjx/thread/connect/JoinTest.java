package com.zjx.thread.connect;

import java.util.Random;

/**
 * join(long) 内部通过wait(long) 实现，
 * join(long) 释放锁
 *
 * Thread.sleep(long) 不释放锁
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/1 15:32
 * @Version V1.0
 **/
public class JoinTest {

    public static void main (String[] args){

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("t1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join(); // 等待t1先执行完
                //t1.join(1000); // 等待时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2");
        });

        t1.start();
        t2.start();
    }
}
