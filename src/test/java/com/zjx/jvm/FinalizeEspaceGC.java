package com.zjx.jvm;

/**
 * 一次对象的自我救赎
 *
 * 任何一个对象的finalize方法只能被调用一次
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/3/9 18:00
 * @Version V1.0
 **/
public class FinalizeEspaceGC {

    public static FinalizeEspaceGC SAVE_HOOK = null;

    public void isAlive(){
        System.out.println("Yes, I'am alive!");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Finalize method executed!");
        FinalizeEspaceGC.SAVE_HOOK = this;
    }

    public static void main (String[] args) throws InterruptedException {
        SAVE_HOOK= new FinalizeEspaceGC();

        // 第一次自救
        SAVE_HOOK = null;
        System.gc();
        // finalize方法优先级很低，等待
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("No, dead");
        }

        // 自救失败
        SAVE_HOOK = null;
        System.gc();
        // finalize方法优先级很低，等待
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("No, dead");
        }
    }
}
