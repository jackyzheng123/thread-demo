package com.zjx.thread.sync;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description Thread基础
 * @Author Carson Cheng
 * @Date 2020/8/28 10:44
 * @Version V1.0
 **/
public class ThreadTest {

    private Integer num = 0;

    //private AtomicInteger num = new AtomicInteger(0);

    @Test
    public void testSync1() {
        final long start = System.currentTimeMillis();
        int nThread = 100;
        CountDownLatch latch = new CountDownLatch(nThread);
        for (int i = 1; i <= nThread; i++) {
            new Thread(() -> {
                for (int j = 1; j <= nThread; j++) {
                    //System.out.println(Thread.currentThread().getName() + ":" + num++);
                    synchronized (ThreadTest.class) {
                        System.out.println(Thread.currentThread().getName() + ":" + num++);
                    }
                    //System.out.println(Thread.currentThread().getName() + ":" + num.incrementAndGet());
                }

                latch.countDown();
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("time: " + (System.currentTimeMillis() - start) + "毫秒");
    }

    @Test
    public void testThread() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        System.out.println("name:" + thread.getName()); // Thread-0
        System.out.println("id:" + thread.getId()); // 12
        System.out.println("isInterrupted:" + thread.isInterrupted()); // false
        System.out.println("isAlive:" + thread.isAlive()); // true
        System.out.println("isDaemon:" + thread.isDaemon()); // false

        thread.interrupt();
        System.out.println("isInterrupted:" + thread.isInterrupted()); // true
        System.out.println("isAlive:" + thread.isAlive()); // true

        System.out.println("Thread.interrupted1:" + Thread.interrupted()); // true
        System.out.println("Thread.interrupted2:" + Thread.interrupted()); // false 连续两次调用interrupted() 清除中断状态

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testThread1() {
        Thread thread = new Thread(() -> {

        });
    }

    @Test
    public void testInterrupt() {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                System.out.println(i);
            }
        });

        try {
            thread.start();
            thread.interrupt();
            Thread.sleep(2000);
            thread.interrupt(); // 没有真正停止线程，只是打个停止标记
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异常法停止线程
     */
    @Test
    public void testInterrupt1() {
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i < 500000; i++) {
                    if (Thread.interrupted()) {
                        System.out.println("停止线程");
                        throw new InterruptedException();
                    }
                    System.out.println(i);
                }
                System.out.println("for循环执行之后");
            } catch (InterruptedException e) {
                System.out.println("抛出异常");
                e.printStackTrace();
            }
        });

        try {
            thread.start();
            Thread.sleep(200);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在沉睡中停止线程
     */
    @Test
    public void testInterrupt2() {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("run begin");
                Thread.sleep(2000000);
                System.out.println("run end");
            } catch (InterruptedException e) {
                System.out.println("在沉睡中停止，进入catch");
                e.printStackTrace();
            }
        });

        try {
            thread.start();
            Thread.sleep(200);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * stop停止
     */
    @Test
    public void testInterrupt3() {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 500000; i++) {
                System.out.println(i);
            }
        });

        try {
            thread.start();
            Thread.sleep(200);
            thread.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * interrupt() return;停止
     */
    @Test
    public void testInterrupt4() {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 500000; i++) {
                System.out.println(i);
                if (Thread.interrupted()) {
                    System.out.println("return 停止");
                    return;
                }
            }
        });

        try {
            thread.start();
            Thread.sleep(200);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * suspend() 暂停
     * resume() 恢复
     *
     * 独占、不同步
     */
    @Test
    public void testSuspendAndResume() {
        AtomicInteger i = new AtomicInteger(0);
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("i:" + i.incrementAndGet());
            }
        });

        try {
            thread.start();
            Thread.sleep(200);

            thread.suspend();
            Thread.sleep(5000);
            System.out.println("暂停5秒");
            thread.resume();
            System.out.println("恢复");

            thread.suspend();
            Thread.sleep(5000);
            System.out.println("暂停5秒");
            thread.resume();
            System.out.println("恢复");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * yield() 礼让
     *
     Thread.yield()方法作用是：暂停当前正在执行的线程对象，并执行其他线程。
     yield()应该做的是让当前运行线程回到可运行状态，以允许具有相同优先级的其他线程获得运行机会。
     因此，使用yield()的目的是让相同优先级的线程之间能适当的轮转执行。
     但是，实际中无法保证yield()达到让步目的，因为让步的线程还有可能被线程调度程序再次选中。

     结论：yield()从未导致线程转到等待/睡眠/阻塞状态。在大多数情况下，yield()将导致线程从运行状态转到可运行状态，但有可能没有效果。
     */
    @Test
    public void testYield() {
        new Thread(() -> {
            System.out.println((Thread.currentThread().getName() + ": yield before..."));
            Thread.yield();
            System.out.println((Thread.currentThread().getName() + ": yield after..."));
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println((Thread.currentThread().getName() + ": hahaha..."));
        }).start();

        try {
            System.out.println(Thread.currentThread().getName() + ":main");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * priority() 优先级1 - 10
     */
    @Test
    public void testPriority() {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "低优先级");
        });
        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "高优先级");
            // 优先级具有继承性
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "继承优先级");
            }).start();
        });

        t1.setPriority(1);
        t2.setPriority(10);
        t1.start();
        t2.start();
    }

    /**
     * 守护线程：Daemon， 当进程不存在非守护线程，守护线程自动销毁， GC(垃圾收集器是守护线程)
     * 用户线程：
     */
    @Test
    public void testDaemon() {
        Thread t1 = new Thread(() -> {
            try {
                int a = 0;
                while (true) {
                    System.out.println(++a);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
        });

        t2.setDaemon(true);
        t2.start();
        t1.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (t2.isAlive()) {
            System.out.println("t1.isAlive():" + t1.isAlive());
        }

        if (t1.isAlive()) {
            System.out.println("t2.isAlive():" + t2.isAlive());
        }
    }

}
