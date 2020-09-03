package com.zjx.thread.lock;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized 与wait() notify() notifyAll()实现等待、通知模式，需要在synchronized同步块或方法中使用
 * ReentrantLock 可以创建多个Condition监视器, 与await() signal() signalAll()实现等待、通知模式，需要在lock.lock()获得监视器后使用
 * <p>
 * notify()  signal()只能通知一个
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/1 17:08
 * @Version V1.0
 **/
public class ReentrantLockTest1 {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();

    @Test
    public void testLockCondition() {
        // 等待
        new Thread(() -> {
            //await();
            awaitA();
        }).start();

        new Thread(() -> {
            //await();
            awaitA();
        }).start();

        new Thread(() -> {
            //await();
            awaitB();
        }).start();

        // 通知
        new Thread(() -> {
            signal();
        }).start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void signal() {
        try {
            lock.tryLock();
            System.out.println(Thread.currentThread().getName() + ": 获得锁, 3秒后通知");
            Thread.sleep(3000);
            // 通知
            //condition.signal(); // 唤醒一个
            //condition.signalAll(); // 唤醒所有
            conditionA.signalAll(); // 唤醒所有A
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + ": 锁释放了");
        }
    }

    private void await() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + ": 获得锁");
            System.out.println("entry await...");
            condition.await();
            System.out.println("exit await...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + ": 锁释放了");
        }
    }

    private void awaitA() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + ": 获得锁");
            System.out.println("entry await...");
            conditionA.await();
            System.out.println("exit await...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + ": 锁释放了");
        }
    }

    private void awaitB() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + ": 获得锁");
            System.out.println("entry await...");
            conditionB.await();
            System.out.println("exit await...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + ": 锁释放了");
        }
    }
}
