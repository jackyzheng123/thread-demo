package com.zjx.thread.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 原子更新基本类型类
 * @Author Carson Cheng
 * @Date 2019/3/13 17:22
 * @Version V1.0
 **/
public class AtomicIntegerTest {

    static AtomicInteger ai = new AtomicInteger(1);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());
        System.out.println(ai.get());
    }
}
