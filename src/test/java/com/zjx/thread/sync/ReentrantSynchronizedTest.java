package com.zjx.thread.sync;

/**
 * @Description synchronized可重入锁
 * @Author Carson Cheng
 * @Date 2020/8/28 17:06
 * @Version V1.0
 **/
public class ReentrantSynchronizedTest extends Thread {

    public synchronized void service1(){
        System.out.println("service1");
        service2();
    }

    public synchronized void service2() {
        System.out.println("service2");
        service3();
    }

    public synchronized void service3() {
        System.out.println("service3");
    }

    @Override
    public void run() {
        super.run();
        service1();
    }

    public static void main (String[] args){
//        ReentrantSynchronizedTest thread = new ReentrantSynchronizedTest();
//        thread.start();

        // 父子类 可重入锁
        new Thread(()->{
            Sub sub = new Sub();
            sub.say();
        }).start();

    }

}

class Parent{
    public int i = 10;
    public synchronized void say(){
        try {
            System.out.println("Parent:" + --i);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Sub extends Parent{

    @Override
    public synchronized void say(){
        try {
            while (i>0){
                System.out.println("Sub:" + --i);
                Thread.sleep(100);
                super.say();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


