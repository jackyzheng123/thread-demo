package com.zjx.thread.util;

/**
 * @Description CountDownLatch允许一个或多个线程等待其他线程完成操作
 * @Author Carson Cheng
 * @Date 2019/3/13 17:32
 * @Version V1.0
 **/
public class CountDownLatchJoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread parser1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("parser1 finish");
            }
        });
        Thread parser2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("parser2 finish");
            }
        });
        parser1.start();
        parser2.start();

        /*
            join用于让当前执行线程等待join线程执行结束,其实现原理是不停检查join线程是否存活，
            如果join线程存活则让当前线程永远等待
            while (isAlive()) {
                wait(0);
            }
         */
        parser1.join();
        parser2.join(3000L); // 等待3秒
        System.out.println("all parser finish");
    }
}
