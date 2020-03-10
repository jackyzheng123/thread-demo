package com.zjx.jvm;

/**
 * @Description 虚拟机栈、本地方法栈OOM测试
 *
 * 如果线程请求的栈深度大于虚拟机所允许的最大深度，将抛出StackOverflowError
 * 如果虚拟机在拓展栈时无法申请到足够的内存，则抛出OutOfMemoryError
 *
 * 使用-Xss参数减少栈内存容量
 *
 * 内存无法分配，抛出StackOverflowError
 * VM Args: -Xss128k
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
