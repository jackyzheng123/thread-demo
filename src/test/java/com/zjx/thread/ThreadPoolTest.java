package com.zjx.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池测试
 *
 * corePoolSize 核心线程数
 * maximumPoolSize 最大线程数
 * keepAliveTime （线程存活保持时间）当线程池中线程数大于核心线程数时，线程的空闲时间如果超过线程存活时间，那么这个线程就会被销毁，直到线程池中的线程数小于等于核心线程数
 * TimeUnit 时间单位
 * BlockingQueue 任务队列
 * ThreadFactory 线程工厂
 * RejectedExecutionHandler 拒绝策略
 *
 * 可以submit()执行，通过future对象获取返回值。
 * 在执行future.get()时，主线程会堵塞，直至当前future线程返回结果。
 *
 * 注：在future调用get方法时，主线程会阻塞(sè)，直到该线程执行完毕返回对象了只有才继续运行。
 * 如果要执行n个线程，可以把future放入Set集合中，在所有线程都启动完毕后，遍历Set取出返回值。
 *
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/11 14:20
 * @Version V1.0
 **/
public class ThreadPoolTest {

    /**
     * 创建线程池
     */
    private static ExecutorService executorService = new ThreadPoolExecutor(10, 50, 0,
            TimeUnit.SECONDS, new SynchronousQueue<>(false), new ThreadFactory() {
        // 线程index
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Thread #" + mCount.getAndIncrement());
        }
    }, new ThreadPoolExecutor.CallerRunsPolicy());


    public static void main(String[] args) {
        int nThread = 10;
        CountDownLatch latch = new CountDownLatch(nThread);
        List<Future> futureList = new ArrayList<>();
        try {
            for (int i = 0; i < nThread; i++) {
                latch.countDown();
                System.out.println(Thread.currentThread().getName() + " Start ...");
                Future<String> future = executorService.submit(() -> doSomething());
                // future.cancel(true);
                // System.out.println("isCancelled:" + future.isCancelled());

                System.out.println(Thread.currentThread().getName() + " isDone:" + future.isDone());
                System.out.println(Thread.currentThread().getName() + " Return:" + future.get());

                System.out.println(Thread.currentThread().getName() + " isDone:" + future.isDone());
                System.out.println(Thread.currentThread().getName() + " End ...");

                futureList.add(future);
            }

            latch.await();

            for(Future future : futureList){
                System.out.println(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static String doSomething() {
        try {
            System.out.println(Thread.currentThread().getName() + " doing");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "done!!!";
    }
}
