package com.zjx.thread.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/2 10:22
 * @Version V1.0
 **/
public class MyTimerTask extends TimerTask {

    @Override
    public void run() {
        System.out.println("系统正在运行。。。");
    }

    public static void main(String[] args) throws ParseException {
        test1();
        //test2();
    }

    /**
     * 运行测试2
     */
    private static void test2() {
        MyTimerTask task = new MyTimerTask();
        Timer timer = new Timer();
        //timer.schedule(task, 5000, 200); // 5秒后每0.2秒运行一次
        timer.scheduleAtFixedRate(task, 5000, 200);

        try {
            Thread.sleep(10000);
            timer.cancel(); // 取消定时器
            System.out.println("定时器取消了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行测试1
     */
    private static void test1() throws ParseException {
        String sdate = "2020-09-02 11:25:00";
        SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date date = sf.parse(sdate);

        MyTimerTask task = new MyTimerTask();
        Timer timer = new Timer();
        //Timer timer = new Timer(true);
        //timer.schedule(task, date); // 指定时间之前，立即运行一次， 之后，在目标时间运行一次
        //timer.schedule(task, date, 200); // 指定时间之前，立即每0.2秒运行一次， 之后，在目标时间每0.2秒运行一次

        timer.scheduleAtFixedRate(task, date, 200);
    }

}
