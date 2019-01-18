package com.zjx.thread.demo;

/**
 * @Description Daemon线程
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