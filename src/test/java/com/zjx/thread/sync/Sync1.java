package com.zjx.thread.sync;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2020/8/28 16:50
 * @Version V1.0
 **/
public class Sync1 extends Thread {

    private String key = "key1";
    private String value = "value1";

    public static void main (String[] args){
        Sync1 sync1 = new Sync1();
        sync1.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sync1.get();

    }

    @Override
    public void run() {
        super.run();
        set("key2", "value2");
    }

    public synchronized void get() {
        System.out.println(key + "=" + value);
    }

    public synchronized void set(String key, String value){
        try {
            this.key = key;
            Thread.sleep(500);
            this.value = value;
            System.out.println(Thread.currentThread().getName() + ":" + key + "=" + value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
