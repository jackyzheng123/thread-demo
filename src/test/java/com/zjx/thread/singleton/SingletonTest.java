package com.zjx.thread.singleton;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * 单例模式
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/2 11:38
 * @Version V1.0
 **/
public class SingletonTest {

    @Test
    public void test1() {
        int nThread = 10;
        CountDownLatch latch = new CountDownLatch(nThread);
        for (int i = 0; i < nThread; i++) {
            new Thread(() -> {
                //System.out.println(MyObject1.getInstance().hashCode());
                //System.out.println(MyObject2.getInstance().hashCode());
                System.out.println(MyObject3.getInstance().hashCode());
                latch.countDown();
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 饿汉式  立即加载
 */
class MyObject1 {
    // 立即加载
    private static MyObject1 myObject1 = new MyObject1();

    private MyObject1() {
    }

    public static MyObject1 getInstance() {
        return myObject1;
    }
}

/**
 * 懒汉式 延迟加载
 */
class MyObject2 {

    private volatile static MyObject2 myObject2;

    private MyObject2() {
    }

    public static MyObject2 getInstance() {
        synchronized (MyObject2.class) {
            if (myObject2 == null) {
                myObject2 = new MyObject2();
            }
            return myObject2;
        }
    }
}

/**
 * 使用static代码块
 */
class MyObject3 {

    private static MyObject3 myObject3;

    private MyObject3() {
    }

    static{
        myObject3 = new MyObject3();
    }

    public static MyObject3 getInstance() {
        return myObject3;
    }
}

/**
 * 使用枚举
 */
enum MyObject4 {

}



