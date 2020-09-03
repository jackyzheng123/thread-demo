package com.zjx.thread.sync;

import java.util.concurrent.CountDownLatch;

/**
 * @Description 计数器 CountDownLatch启动和停止线程
 * @Author Carson Cheng
 * @Date 2020/8/26 10:42
 * @Version V1.0
 **/
public class Test1 {

    public static void main (String[] args){
        timeTask(10);
    }

    /**
     * 线程数
     * @param count
     */
    private static void timeTask(Integer count) {
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(count);

        for (int i=1; i<= count; i++) {
            new Thread(()->{
                try {
                    start.await();
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + "休息了两秒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    end.countDown();
                }
            }).start();
        }

        final long startTime = System.currentTimeMillis();
        try {
            start.countDown();
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final long endTime = System.currentTimeMillis();

        System.out.println("总耗时：" + (endTime - startTime)/1000 + "秒");
    }

}
