package com.zjx.jvm;

/**
 * 测试--判断对象是否存活 是用引用计数算法？
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/3/9 17:54
 * @Version V1.0
 **/
public class ReferenceCountingGC {

    private Object instance = null;

    private static final int _1MB = 1024 * 1024;

    private static void testGC() {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();

        objA.instance = objB;
        objB.instance = objA;

        // 假设这时发生gc，objA和objB能否被回收
        System.gc();

        System.out.println("done");
    }

    public static void main(String[] args) {
        testGC();
    }
}
