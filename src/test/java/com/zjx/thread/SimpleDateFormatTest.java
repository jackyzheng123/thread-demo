package com.zjx.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * SimpleDateFormat 线程不安全
 *
 * 解决一：创建多例
 * 解决二：ThreadLocal解决
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/2 15:01
 * @Version V1.0
 **/
public class SimpleDateFormatTest {

    //private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static String[] dateStrArr = {"2020-09-02", "2020-09-01", "2020-08-31", "2020-08-30", "2020-08-29",
            "2020-08-28", "2020-08-27", "2020-08-26", "2020-08-25", "2020-08-24", "2020-08-23", "2020-08-22"};

    public static void main (String[] args){
        int length = dateStrArr.length;
        CountDownLatch latch = new CountDownLatch(length);
        for (int i = 0; i < length; i++) {
            final int n = i;
            new Thread(()->{
                try {
                    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 解决一：创建多例
                    SimpleDateFormat sdf = DateTools.getInstance(); // ThreadLocal解决
                    final Date parse = sdf.parse(dateStrArr[n]);
                    final String newDateStr = sdf.format(parse);
                    if (!newDateStr.equals(dateStrArr[n])) {
                        System.out.println(Thread.currentThread().getName() + "日期转换错误，dateStr：" + dateStrArr[n] + ", newDateStr:" + newDateStr);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                latch.countDown();

            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

/**
 * ThreadLocal解决
 */
class DateTools{
    private static ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(()-> new SimpleDateFormat("yyyy-MM-dd"));

    public static SimpleDateFormat getInstance(){
        return threadLocal.get();
    }
}
