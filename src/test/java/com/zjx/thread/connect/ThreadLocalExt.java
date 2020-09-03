package com.zjx.thread.connect;

import java.util.Date;
import java.util.function.Supplier;

/**
 * ThreadLocal
 *
 * InheritableThreadLocal可以让子线程从父线程取值
 * childValue()
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/1 16:21
 * @Version V1.0
 **/
public class ThreadLocalExt extends ThreadLocal {

    @Override
    protected Object initialValue() {
        return "我是初始值";
    }

    public static void main (String[] args){
        ThreadLocal threadLocal = new ThreadLocal();
        ThreadLocal initThreadLocal = ThreadLocal.withInitial(()-> "123");
        ThreadLocalExt threadLocalExt = new ThreadLocalExt();
        ThreadLocal inheritableThreadLocal = InheritableThreadLocal.withInitial(Date::new);

        System.out.println(threadLocal.get());
        System.out.println(initThreadLocal.get());
        System.out.println(threadLocalExt.get());
        System.out.println(inheritableThreadLocal.get());

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + ",初始值：" + initThreadLocal.get());
            initThreadLocal.set("456");
            System.out.println(Thread.currentThread().getName() + ",改后：" + initThreadLocal.get());
        }).start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + ",初始值：" + initThreadLocal.get());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ",改后：" + initThreadLocal.get());
        }).start();


        try {
            Thread.sleep(5000);
            threadLocal.remove();
            threadLocalExt.remove();
            threadLocal.remove();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
