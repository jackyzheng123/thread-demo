package com.zjx.thread.demo;

import java.util.concurrent.TimeUnit;

/**
 * 应用：AOP方法执行前后调用begin end方法
 *
 * @Description ThreadLocal
 * @Author Carson Cheng
 * @Date 2019/1/17 16:20
 * @Version V1.0
 **/
public class ThreadLocalTest {
    // 第一次get()方法调用时会进行初始化（如果set方法没有调用），每个线程会调用一次
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static final void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws Exception {
        ThreadLocalTest.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost: " + ThreadLocalTest.end() + " mills");
    }
}
