package com.zjx.thread.demo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 避免一个线程同时获取多个锁。
 * 避免一个线程在锁内同时占用多个资源，尽量保证每个锁只占用一个资源。
 * 尝试使用定时锁，使用lock.tryLock（timeout）来替代使用内部锁机制。
 * 对于数据库锁，加锁和解锁必须在一个数据库连接里，否则会出现解锁失败的情况。
 *
 *
 * demo:
 * 线程t1: 锁住A资源，等待B资源释放
 * 线程t2: 锁住B资源，等待A资源释放
 *
 * synchronized:
 * 原子性： 确保线程互斥的访问同步代码；
 * 可见性： 保证共享变量的修改能够及时可见，其实是通过Java内存模型中的 “对一个变量unlock操作之前，必须要同步到主内存中；如果对一个变量进行lock操作，则将会清空工作内存中此变量的值，在执行引擎使用此变量前，需要重新从主内存中load操作或assign操作初始化变量值” 来保证的；
 * 有序性： 有效解决重排序问题，即 “一个unlock操作先行发生(happen-before)于后面对同一个锁的lock操作”；
 *
 * synchronized可以把任何一个非null对象作为"锁"，在HotSpot JVM实现中，锁有个专门的名字：对象监视器（Object Monitor）。
 *
 * synchronized三种用法：
 * 1. 当synchronized作用在实例方法时，监视器锁（monitor）便是对象实例（this）；
 * 2. 当synchronized作用在静态方法时，监视器锁（monitor）便是对象的Class实例，因为Class数据存在于永久代，因此静态方法锁相当于该类的一个全局锁；
 * 3. 当synchronized作用在某一个对象实例时，监视器锁（monitor）便是括号括起来的对象实例；
 *
 * synchronized 内置锁 是一种 对象锁（锁的是对象而非引用变量），作用粒度是对象 ，可以用来实现对 临界资源的同步互斥访问 ，
 * 是 可重入 的。其可重入最大的作用是避免死锁，如： 子类同步方法调用了父类同步方法，如没有可重入的特性，则会发生死锁；
 *
 *
 *
 * @Description 死锁测试
 * @Author Carson Cheng
 * @Date 2019/1/17 11:37
 * @Version V1.0
 **/
public class DeadLockDemo {
    private String A = "A";
    private String B = "B";

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        // synchronized死锁
        //new DeadLockDemo().deadLock();

        // ReentrantLock死锁
        //new DeadLockDemo().deadLock1();

        // ReadWriteLock
        //new DeadLockDemo().readWriteLock1();
        new DeadLockDemo().readWriteLock2();
    }

    /**
     * ReadWriteLock死锁
     * 锁降级：从写锁变成读锁；
     * 锁升级：从读锁变成写锁
     * 同一个线程中，在没有释放读锁的情况下，就去申请写锁，这属于锁升级，ReentrantReadWriteLock是不支持的锁升级。
     */
    private void readWriteLock1() {
        rwLock.readLock().lock();
        System.out.println("get read lock");
        rwLock.writeLock().lock();
        System.out.println("blocking");
    }

    /**
     * ReentrantReadWriteLock支持锁降级
     * 从写锁降级成读锁
     */
    private void readWriteLock2() {
        rwLock.writeLock().lock();
        System.out.println("get write lock");
        rwLock.readLock().lock();
        System.out.println("blocking");
    }

    /**
     * ReentrantLock死锁
     */
    private void deadLock1() {
        Thread t1 = new Thread(() -> {
            try {
                lock1.lock();
                Thread.sleep(2000);
                lock2.lock();
            } catch(InterruptedException e) {

            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                lock2.lock();
                lock1.lock();
            } catch(Exception e) {

            } finally {
                lock2.unlock();
                lock1.unlock();
            }
        });

        t1.start();
        t2.start();
    }

    /**
     * synchronized死锁
     */
    private void deadLock() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A) {
                    try {
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (B) {
                        System.out.println("1");
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B) {
                    synchronized (A) {
                        System.out.println("2");
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }
}