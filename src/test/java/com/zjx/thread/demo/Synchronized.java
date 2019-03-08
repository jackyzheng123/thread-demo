package com.zjx.thread.demo;

/**
 * @Description 通过使用javap工具查看生成的class文件信息来分析synchronized关键字的实现细节
 * @Author Carson Cheng
 * @Date 2019/1/17 15:29
 * @Version V1.0
 **/
public class Synchronized {

    public static void main(String[] args) {
        synchronized (Synchronized.class) {
        }
        syncMethod();
    }

    private static synchronized void syncMethod() {
    }

}
