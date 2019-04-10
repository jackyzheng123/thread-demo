package com.zjx.thread.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Description 控制并发线程数
 * Semaphore（信号量）是用来控制同时访问特定资源的线程数量，它通过协调各个线程，以保证合理的使用公共资源
 * <p>
 * 应用场景：
 * Semaphore可以用于做流量控制，特别是公用资源有限的应用场景，比如数据库连接。
 * @Author Carson Cheng
 * @Date 2019/3/13 18:17
 * @Version V1.0
 **/
public class SemaphoreTest {
    private static final int THREAD_COUNT = 30;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
    private static Semaphore s = new Semaphore(10);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        s.acquire(); // acquire()用来获取一个许可，若无许可能够获得，则会一直等待，直到获得许可。
                        System.out.println("save data");
                        s.release(); // release()用来释放许可。注意，在释放许可之前，必须先获获得许可。
                    } catch (InterruptedException e) {
                    }
                }
            });
        }
        threadPool.shutdown();
    }
}
