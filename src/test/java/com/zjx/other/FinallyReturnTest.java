package com.zjx.other;

/**
 * @Description finally是在return执行之后，return返回之前执行
 * @Author Carson Cheng
 * @Date 2019/4/9 10:45
 * @Version V1.0
 **/
public class FinallyReturnTest {

    public static void main(String[] args) {
        System.out.println(test());
    }

    private static int test() {
        int b = 20;
        try {
            System.out.println("try block");
            return b += 80;
        } catch (Exception e) {
            System.out.println("catch block");
        } finally {
            System.out.println("finally block");
            if (b > 25) {
                System.out.println("b>25,  b=" + b);
            }
            return b;
        }
    }
}
