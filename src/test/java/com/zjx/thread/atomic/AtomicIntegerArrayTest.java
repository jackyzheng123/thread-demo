package com.zjx.thread.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Description 原子更新数组
 * @Author Carson Cheng
 * @Date 2019/3/13 17:24
 * @Version V1.0
 **/
public class AtomicIntegerArrayTest {
    static int[] value = new int[]{1, 2};
    static AtomicIntegerArray ai = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        ai.getAndSet(0, 3); // 替换索引为0的值
        System.out.println(ai.get(0));
        System.out.println(value[0]);
    }
}
