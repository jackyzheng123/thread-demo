package com.zjx.thread.jvm;

/**
 * @Description 测试内存分配、回收策略
 * @Author Carson Cheng
 * @Date 2019/3/25 17:39
 * @Version V1.0
 **/
public class GCTest {

    private static final int _1MB = 1024 * 1024;

    /**
     * VM参数：
     * -Xms20M 最小堆内存
     * -Xmx20M 最大堆内存
     * -Xmn10M 新生代内存
     * -XX:+PrintGCDetails 收集器日志参数
     * -XX:SurvivorRatio=8 新生代中Eden区与一个Survivor区空间比例为8:1, 即新生代10M中，Eden区占了8M
     * <p>
     * 最大堆内存与最小堆内存设置成一样限制了Java堆大小为20M，不可扩展
     * Eden区与Survivor区（2个）占比80%和20%
     * 两个Survivor区最大的好处就是解决了碎片化
     * 永远有一个survivor space是空的，另一个非空的survivor space无碎片
     */
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;

        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB]; // 出现一次Minor GC
    }

    /**
     * 大对象直接进入老年代
     * --XX:PretenureSizeThreshold=3145728  (3M) 设置大于3M的对象直接进入老年代
     */
    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB];
    }

    /**
     * 长期存活的对象进入老年代
     * <p>
     * -Xms20M 最小堆内存
     * -Xmx20M 最大堆内存
     * -Xmn10M 新生代内存
     * -XX:+PrintGCDetails 收集器日志参数
     * -XX:SurvivorRatio=8 新生代中Eden区与一个Survivor区空间比例为8:1, 即新生代10M中，Eden区占了8M
     * -XX:MaxTenuringThreshold=1 对象晋升老年代的阈值（默认是15），对象每次在Eden区经过一次Minor GC仍存活进入Survivor区，对象年龄就加一
     */
    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;

        allocation1 = new byte[_1MB / 4];
        // 什么时候进入老年代取决于-XX:MaxTenuringThreshold设置
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }

    /**
     * 动态对象年龄判定
     * Survivor区相同年龄的所有对象大小总和大于Survivor区的一半，年龄大于或等于该年龄的对象直接进入老年代，无须等到MaxTenuringThreshold要求的年龄
     *
     * <p>
     * -XX:MaxTenuringThreshold=15
     */
    public static void testTenuringThreshold2() {
        byte[] allocation1, allocation2, allocation3, allocation4;

        allocation1 = new byte[_1MB / 4];
        // allocation1 + allocation2 大于survivor空间的一半
        allocation2 = new byte[_1MB / 4];
        allocation3 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB];
        allocation4 = null;
        allocation4 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
//        testAllocation();
//        testPretenureSizeThreshold();
//        testTenuringThreshold();
        testTenuringThreshold2();
    }

}
