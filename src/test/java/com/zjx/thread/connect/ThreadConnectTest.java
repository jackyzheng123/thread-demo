package com.zjx.thread.connect;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Description 线程停不掉
 * @Author Carson Cheng
 * @Date 2020/8/31 17:58
 * @Version V1.0
 **/
public class ThreadConnectTest {

    private List<String> list = new ArrayList<>();

    @Test
    public void test() {
        CountDownLatch latch = new CountDownLatch(2);
        new Thread(() -> {
            add();
            latch.countDown();
        }).start();

        new Thread(() -> {
            stop();
            latch.countDown();
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        try {
            while (true) {
                if (list.size() == 5) {
                    throw new InterruptedException("超过大小");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void add() {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("添加第" + i + "个");
                list.add("xxx");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
