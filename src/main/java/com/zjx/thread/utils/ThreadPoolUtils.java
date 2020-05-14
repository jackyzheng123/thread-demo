package com.zjx.thread.utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuya
 * @description
 * @Date 2019/8/15
 */
public class ThreadPoolUtils {

    /**
     * cpu个数
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池运行的核心线程数
     */
    private static final int CORE_POOL_SIZE = Math.max(2, Math.max(CPU_COUNT, 4));

    /**
     * 线程池最大线程数
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    /**
     * 线程空闲后的存活时长
     */
    private static final int KEEP_ALIVE = 3;

    /**
     * 采用ThreadFactory管理线程
     */
    private static final ThreadFactory threadFactory = new ThreadFactory() {
        /**
         * 线程index
         */
        private final AtomicInteger count = new AtomicInteger(1);

        /**
         * {@inheritDoc}
         */
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Framework lib Thread #" + count.getAndIncrement());
        }
    };

    /**
     * 提交到线程池的Runnable队列
     */
    private static final BlockingQueue<Runnable> poolWorkQueue = new LinkedBlockingQueue<Runnable>(10);

    /**
     * 线程池
     */
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
        KEEP_ALIVE, TimeUnit.SECONDS, poolWorkQueue, threadFactory);

    /**
     * 获取线程池
     * 
     * @return 线程池
     */
    public static ThreadPoolExecutor getExecutor() {
        return executor;
    }

    /**
     * 关闭线程池
     */
    public static void shutdown() {
        if (!executor.isShutdown()) {
            executor.shutdown();
        }
    }
}
