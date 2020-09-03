package com.zjx.thread.demo;

/**
 * <p>
 * ThreadLocal源码中的存储的是Map的结构（ThreadLocalMap<ThreadLocal, value>），
 * key是弱引用，value是强引用，因为如果触发GC，弱引用会被标记回收，也就是说，
 * 这个ThreadLocal没法被使用，但又会有强引用的存在，又不会被回收，因此就处于需要回收，
 * 但又回收不掉的状态，就会出现内存泄漏
 * <p>
 *
 * 参考WeakRefDemo
 * 强引用：普通的引用，强引用指向的对象不会被回收；
 * 软引用：仅有软引用指向的对象，只有发生gc且内存不足，才会被回收；
 * 弱引用：仅有弱引用指向的对象，只要发生gc就会被回收。
 *
 * @Description ThreadLocal
 * @Author Carson Cheng
 * @Date 2019/1/17 16:20
 * @Version V1.0
 **/
public class ThreadLocalTest {

    private final static ThreadLocal threadLocal = new ThreadLocal();

    public static void main(String[] args) {
        threadLocal.set("hello");
        System.out.println("main thread:" + threadLocal.get());

        new Thread(() -> {
            threadLocal.set("zjx");
            System.out.println("new Thread1:" + threadLocal.get());
        }).start();

        new Thread(() -> {
            threadLocal.set("carson");
            System.out.println("new Thread2:" + threadLocal.get());
        }).start();
    }
}
