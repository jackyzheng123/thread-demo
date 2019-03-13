package com.zjx.thread.arithmetic;

import org.junit.Test;

/**
 * @Description 大数据计算
 * @Author Carson Cheng
 * @Date 2019/3/12 9:15
 * @Version V1.0
 **/
public class BigDataTest {

    /**
     * 计算5的阶乘
     */
    @Test
    public void test1() {
        int n = 5;
        int num = 1;
        for (int i = 1; i <= n; i++) {
            num *= i;
        }
        System.out.println(num);
    }

    /**
     * 计算489*18
     */
    @Test
    public void test2() {
        // 489用数组存储
        int[] arr = new int[6];
        arr[arr.length - 1] = 9;
        arr[arr.length - 2] = 8;
        arr[arr.length - 3] = 4;
        int num = 18;

        cal(arr, num);
        // 遍历
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
    }

    /**
     * 计算 50!
     */
    @Test
    public void test3() {
        int[] arr = new int[100];
        int n = 50;
        arr[arr.length - 1] = 1; //乘数

        //计算阶乘
        for (int i = 1; i <= n; i++) {
            arr = cal(arr, i);
        }
        // 遍历
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
    }

    /**
     * 计算大数据
     *
     * @param arr
     * @param num
     * @return
     */
    public int[] cal(int[] arr, int num) {
        // 数组与num相乘
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= num;
        }
        // 进留
        for (int i = arr.length - 1; i > 0; i--) {
            arr[i - 1] += arr[i] / 10;
            arr[i] %= 10;
        }
        return arr;
    }

}
