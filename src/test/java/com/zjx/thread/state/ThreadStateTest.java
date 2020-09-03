package com.zjx.thread.state;

import org.junit.Test;

/**
 * 线程状态测试
 * NEW
 * RUNNABLE
 * BLOCKED
 * WAITING
 * TIMED_WAITING
 * TERMINATED
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/2 14:19
 * @Version V1.0
 **/
public class ThreadStateTest {

    private Object object = new Object();

    @Test
    public void testState (){
        Thread thread1 = new Thread(()->{
            test();
        });

        Thread thread2 = new Thread(()->{
            test();
        });

        System.out.println("thread1 before start: " + thread1.getState());// NEW
        thread1.start();
        System.out.println("thread1 starting: " + thread1.getState());// RUNNABLE

        thread2.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("thread1 sleep: " + thread1.getState()); // TIMED_WAITING
        System.out.println("thread2 state:" + thread2.getState()); // BLOCKED

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("thread1 sleep: " + thread1.getState()); // TERMINATED  WAITING
    }

    private void test() {
        synchronized (object) {
            try {
                System.out.println(Thread.currentThread().getName() + ", before sleep: " + Thread.currentThread().getState()); // RUNNABLE
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + ", after sleep: " + Thread.currentThread().getState()); // RUNNABLE

                System.out.println(Thread.currentThread().getName() + ", before wait: " + Thread.currentThread().getState()); // RUNNABLE
                object.wait();
                System.out.println(Thread.currentThread().getName() + ", after wait: " + Thread.currentThread().getState()); // 没有

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
