package com.zjx.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Description 本机直接内存溢出
 * <p>
 * -Xmx20M
 * -XX:MaxDirectMemorySize=10M
 *
 *
 * @Author Carson Cheng
 * @Date 2019/3/19 17:46
 * @Version V1.0
 **/
public class DirectMemoryOOM {

    private static final int ONE_MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(ONE_MB);
        }
    }
}
