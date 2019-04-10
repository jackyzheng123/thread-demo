package com.zjx.thread.util;

import java.util.concurrent.Semaphore;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2019/4/9 11:12
 * @Version V1.0
 **/
public class SemaphoreTest1 {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 100; i++) {
            new Thread(new Worker(semaphore, i)).start();
        }
    }

    static class Worker implements Runnable {
        Semaphore semaphore;
        int num;

        public Worker(Semaphore semaphore, int i) {
            this.semaphore = semaphore;
            this.num = i;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                semaphore.acquire(); // acquire()用来获取一个许可，若无许可能够获得，则会一直等待，直到获得许可。
                System.out.println("工人" + this.num + "占用一个机器在生产...");
                System.out.println("工人" + this.num + "释放出机器");
                semaphore.release(); // release()用来释放许可。注意，在释放许可之前，必须先获获得许可。
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
