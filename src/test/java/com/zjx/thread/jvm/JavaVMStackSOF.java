package com.zjx.thread.jvm;

/**
 * @Description 虚拟机栈、本地方法栈OOM测试
 *
 * 内存无法分配，抛出StackOverflowError
 *
 * @Author Carson Cheng
 * @Date 2019/3/19 16:22
 * @Version V1.0
 **/
public class JavaVMStackSOF {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main (String[] args){
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stackLength=" + oom.stackLength);
            throw e;
        }
    }
}
