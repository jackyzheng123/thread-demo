package com.zjx.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 当线程请求的栈的深度大于虚拟机所允许的最大深度，则抛出StackOverflowError
 *
 * 如果虚拟机在扩展栈时无法申请到足够的内存空间，则抛出OutOfMemoryError
 *
 * 递归调用导致StackOverflowError
 * 变量/局部变量过大导致栈内存溢出OutOfMemoryError
 *
 * 设置栈深度
 * -Xss128k
 * 最小108k
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/8/26 15:49
 * @Version V1.0
 **/
public class JavaVMStackOverflowError {

    // 栈深度
    private static int a = 0;

    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        try {
            //递归调用导致StackOverflowError
            //incr();

            //局部变量过大导致栈内存溢出OutOfMemoryError
            bigParams();

        } catch (Throwable e) {
            System.out.println("栈深度：" + a);
            System.out.println("list大小：" + list.size());
            e.printStackTrace();
        }
    }

    private static void bigParams() {
        // List<String> list = new ArrayList<>();
        for (; ; ) {
            list.add("test");
        }
    }

    private static void incr() {
        a++;
        incr();
    }
}
