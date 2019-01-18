package com.zjx.thread.demo;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2019/1/17 14:54
 * @Version V1.0
 **/
public class SleepUtils {

    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }
}
