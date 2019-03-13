package com.zjx.thread.util;

import java.util.concurrent.CyclicBarrier;

/**
 * @Description 同步屏障
 * <p>
 * 让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，
 * 屏障才会开门，所有被屏障拦截的线程才会继续运行
 * @Author Carson Cheng
 * @Date 2019/3/13 17:51
 * @Version V1.0
 **/
public class CyclicBarrierTest {
    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await(); // 当前线程到达屏障，被阻塞
                } catch (Exception e) {
                }
                System.out.println(1);
            }
        }).start();
        try {
            c.await(); // 当前线程到达屏障，被阻塞
        } catch (Exception e) {
        }
        System.out.println(2);
    }
}