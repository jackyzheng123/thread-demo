package com.zjx.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description OOM测试并分析
 *
 * -Xms20m 最小堆内存大小
 * -Xmx20m 最大堆内存大小
 *
 *  生成java_pid12696.hprof
 * -XX:+HeapDumpOnOutOfMemoryError
 *
 * 分析：
 * 1. 下载Memory Analyzer
 *
 * @Author Carson Cheng
 * @Date 2019/3/19 15:23
 * @Version V1.0
 **/
public class HeapOOM {

    public static void main (String[] args){
        List<Object> list = new ArrayList<Object>();
        while (true) {
            list.add(new Object());
        }
    }
}
