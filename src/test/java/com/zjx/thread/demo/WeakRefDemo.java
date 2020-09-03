package com.zjx.thread.demo;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 强引用：普通的引用，强引用指向的对象不会被回收；
 * 软引用：仅有软引用指向的对象，只有发生gc且内存不足，才会被回收；
 * 弱引用：仅有弱引用指向的对象，只要发生gc就会被回收。
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/8/31 15:08
 * @Version V1.0
 **/
public class WeakRefDemo {

    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();


        Object strongA = a;
        SoftReference<Object> softB = new SoftReference<>(b);
        WeakReference<Object> weakC = new WeakReference<>(c);

        a = null;
        b = null;
        c = null;

        System.out.println("Before GC...");
        System.out.println("strongA:" + strongA);
        System.out.println("softB:" + softB.get());
        System.out.println("weakC:" + weakC.get());

        System.out.println("Run GC...");
        System.gc();

        System.out.println("strongA:" + strongA);
        System.out.println("softB:" + softB.get());
        System.out.println("weakC:" + weakC.get());
    }
}
