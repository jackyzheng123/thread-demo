package com.zjx.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 运行时常量池导致的OOM
 *
 * 设置方法区内存大小：
 * -XX:PermSize=10M
 * -XX:MaxPermSize=10M
 *
 *
 * @Author Carson Cheng
 * @Date 2019/3/19 17:05
 * @Version V1.0
 **/
public class RuntimeConstantPoolOOM {

    public static void main (String[] args){
        List<String> list = new ArrayList<>();

        int i = 0;
        while (true){
            // String.intern(): 如果String常量池中有此String对象则返回，否则将此String添加到常量池中，并返回String对象的引用
            System.out.println(i);
            list.add(String.valueOf(i++).intern());
        }
    }
}
