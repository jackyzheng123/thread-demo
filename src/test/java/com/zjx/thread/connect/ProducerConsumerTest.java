package com.zjx.thread.connect;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者-消费者模式
 *
 * 案例：期望生产一个商品就被消费掉，再生产下一个商品
 *
 * 问题一：消费早期商品。使用同步函数：将set方法和out方法加上synchronized关键字或同步代码块，问题解决。
 *
 * 问题二：同一个商品重复消费，疯狂生产和疯狂消费。
 * 分析：生产者什么时候生产呢？当容器中没有面包时，就生产，否则不生产；
 * 消费者什么时候消费呢？当容器中有面包时，就消费，否则不消费。
 * 生产者生产了商品后，通知消费者来消费，这时的生产者应该处于等待状态；
 * 消费者消费了商品后，通知生产者生产，这时消费者应该处于等待状态。
 *
 * 等待：wait();　　通知：notify();
 *
 * Lock解决：
 *
 * jdk1.5将原有的监视器方法wait(),notify(),notifyAll封装到Condition对象中。
 * Condition对象的出现其实是替代了Object中的监视器方法。
 * await();    signal();    signalAll();
 * 旧版中通知所有的方法效率低。
 * jdk1.5以后，可以在一个lock锁上加上多个监视器对象。
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/1 11:57
 * @Version V1.0
 **/
public class ProducerConsumerTest {

    public static void main(String[] args) {
        //1、创建资源对象
        Resource r = new Resource();
        //2、创建线程任务
        Producer p = new Producer(r);
        Consumer c = new Consumer(r);
        //3、创建线程对象
        Thread t1 = new Thread(p);
        Thread t2 = new Thread(p);
        Thread t3 = new Thread(c);
        Thread t4 = new Thread(c);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

//1、描述资源
class Resource {
    private String name;
    private int count = 1;
    //定义一个锁对象
    private Lock lock = new ReentrantLock();
    //获取锁上的Condition对象，为了解决本方通知对象问题，可以一个锁创建两个监视器对象。
    private Condition consu = lock.newCondition();//获取lock上的监视器方法,负责消费
    private Condition produ = lock.newCondition();//获取lock上的监视器方法，负责生产

    //对象锁
    //private Object object = new Object();

    //定义标记
    private boolean flag = false;

    public void set(String name) {//t1,t2
        //获取锁
        lock.lock();
        //synchronized (object){
        try {
            while (flag) { // 生产者：有产品了，暂停
                try {
                    produ.await();
                    //object.wait();
                } catch (InterruptedException e) {
                }
            }
            //给成员变量赋值并加上编号
            this.name = name + count;
            //编号自增
            count++;
            //打印生产了哪个商品
            System.out.println(Thread.currentThread().getName() + "...生产者.." + this.name);
            flag = true;
            //通知消费者
            consu.signal();
            //object.notify();
        } finally {
            lock.unlock();//一定要执行
        }

    }

    public void out() {//t3,t4
        //获取锁
        lock.lock();
        //synchronized (object){
        try {
            while (!flag) { // 消费者：没有产品了，暂停
                try {
                    consu.await();
                    //object.wait();
                } catch (InterruptedException e) {
                }
            }
            System.out.println(Thread.currentThread().getName() + "...消费者.." + this.name);
            flag = false;
            //通知生产者
            produ.signal();
            //object.notify();
        } finally {
            lock.unlock();//一定要执行
        }
    }
}

//2、描述生产者
class Producer implements Runnable {
    private Resource r;

    public Producer(Resource r) {
        this.r = r;
    }

    @Override
    public void run() {
        while (true) {
            r.set("面包");
        }
    }
}

//3、描述消费者
class Consumer implements Runnable {
    private Resource r;

    public Consumer(Resource r) {
        this.r = r;
    }

    @Override
    public void run() {
        while (true) {
            r.out();
        }
    }
}