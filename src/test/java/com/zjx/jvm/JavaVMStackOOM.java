package com.zjx.jvm;

/**
 * @Description 循环创建线程导致OOM
 *
 * -Xss2M 设置每个线程的栈大小
 *
 * @Author Carson Cheng
 * @Date 2019/3/19 16:33
 * @Version V1.0
 **/
public class JavaVMStackOOM {

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakThread();
    }

    public void stackLeakThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    private void dontStop() {
        while (true) {
        }
    }
}
