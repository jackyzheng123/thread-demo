package com.zjx.thread.demo;

/**
 * @Description Daemon线程
 *
 * 守护线程，是指在程序运行的时候在后台提供一种通用服务的线程，比如垃圾回收线程就是一个很称职的守护者
 *
 * (1) thread.setDaemon(true)必须在thread.start()之前设置，否则会跑出一个IllegalThreadStateException异常。你不能把正在运行的常规线程设置为守护线程。
 * (2) 在Daemon线程中产生的新线程也是Daemon的。
 * (3) 守护线程应该永远不去访问固有资源，如文件、数据库，因为它会在任何时候甚至在一个操作的中间发生中断。
 *
 * @Author Carson Cheng
 * @Date 2019/1/17 15:02
 * @Version V1.0
 **/
public class Daemon {
    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true); // 将线程设置为Daemon线程。finally块并不一定会执行
        thread.start();
    }

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                SleepUtils.second(10);
            } finally {
                System.out.println("DaemonThread finally run.");
            }
        }
    }
}