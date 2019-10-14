package com.zjx.thread.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Description 独占式， 队列同步器AQS接口示例
 * @Author Carson Cheng
 * @Date 2019/1/18 11:09
 * @Version V1.0
 **/
class Mutex implements Lock {
    // 静态内部类，自定义同步器
    private static class Sync extends AbstractQueuedSynchronizer {
        // 是否处于占用状态
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        //当状态为0的时候获取锁，CAS操作成功，则state状态为1，
        public boolean tryAcquire(int acquires) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        // 释放锁，将状态设置为0
        protected boolean tryRelease(int releases) {
            if (getState() == 0) throw new
                    IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        // 返回一个Condition，每个condition都包含了一个condition队列
        Condition newCondition() {
            return new ConditionObject();
        }
    }

    // 仅需要将操作代理到Sync上即可
    private final Sync sync = new Sync();

    //加锁操作，代理到acquire（模板方法）上就行，acquire会调用我们重写的tryAcquire方法
    public void lock() {
        sync.acquire(1);
    }

    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    //释放锁，代理到release（模板方法）上就行，release会调用我们重写的tryRelease方法。
    public void unlock() {
        sync.release(1);
    }

    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }


    /**
     * 测试
     */
    private static CyclicBarrier barrier = new CyclicBarrier(31);
    private static int a = 0;
    private static  Mutex mutex = new Mutex();

    public static void main (String[] args) throws BrokenBarrierException, InterruptedException {
        //说明:我们启用30个线程，每个线程对i自加10000次，同步正常的话，最终结果应为300000；
        //未加锁前
        for(int i=0;i<30;i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<10000;i++){
                        increment1();//没有同步措施的a++；
                    }
                    try {
                        barrier.await();//等30个线程累加完毕
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
        barrier.await();
        System.out.println("加锁前，a="+a);

        //加锁后
        barrier.reset();//重置CyclicBarrier
        a=0;
        for(int i=0;i<30;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<10000;i++){
                        increment2();//a++采用Mutex进行同步处理
                    }
                    try {
                        barrier.await();//等30个线程累加完毕
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        barrier.await();
        System.out.println("加锁后，a="+a);
    }
    /**
     * 没有同步措施的a++
     * @return
     */
    public static void increment1(){
        a++;
    }
    /**
     * 使用自定义的Mutex进行同步处理的a++
     */
    public static void increment2(){
        mutex.lock();
        a++;
        mutex.unlock();
    }

}
