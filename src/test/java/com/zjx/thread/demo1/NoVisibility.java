package com.zjx.thread.demo1;

/**
 * 重排序
 * volatile
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/8/20 15:34
 * @Version V1.0
 **/
public class NoVisibility {

    private static boolean flag;
    private static Integer num;

    public static void main (String[] args){
        new Thread(() -> {
            while (!flag) {
                Thread.yield();
                System.out.println("线程运行-》就绪");
            }
            System.out.println(num);
        }).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        num = 42;
        flag = true;
    }
}
