package com.zjx.thread.lock;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock
 * 读写锁：读锁（共享锁），写锁（排他锁）
 * 读-读不互斥，读-写互斥，写-写互斥
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/2 9:45
 * @Version V1.0
 **/
public class ReentrantReadWriteLockTest {

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    @Test
    public void test1() {
        new Thread(() -> {
            //read();
            write();
        }).start();

        new Thread(() -> {
            read();
            //write();
        }).start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void read() {
        try {
            readLock.lock();
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + ": 获取读锁");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + ": 释放读锁");
        }
    }

    private void write() {
        try {
            writeLock.lock();
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + ": 获取写锁");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
            System.out.println(Thread.currentThread().getName() + ": 释放写锁");
        }
    }

}
