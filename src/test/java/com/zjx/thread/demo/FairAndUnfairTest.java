package com.zjx.thread.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 公平锁与非公平锁获取的区别
 * @Author Carson Cheng
 * @Date 2019/1/18 11:34
 * @Version V1.0
 **/
public class FairAndUnfairTest {

    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unfairLock = new ReentrantLock2(false);

    @Test
    public void fair() {
        testLock(fairLock);
    }

    @Test
    public void unfair() {
        testLock(unfairLock);
    }

    private void testLock(Lock lock) {
// 启动5个Job（略）
        for (int i = 0; i < 5; i++) {
            Job job= new Job(lock);
            job.start();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Job extends Thread {
        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        public void run() {
// 连续2次打印当前的Thread和等待队列中的Thread（略）
            System.out.println(Thread.currentThread());
            System.out.println(Thread.getAllStackTraces());
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<Thread>(super.
                    getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }
}
