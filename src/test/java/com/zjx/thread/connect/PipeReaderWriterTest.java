package com.zjx.thread.connect;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 字符流 - 通过管道进行线程间通信
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/1 14:17
 * @Version V1.0
 **/
public class PipeReaderWriterTest {

    public static void main(String[] args) {

        try {
            //reader.connect(writer);
            writer.connect(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            readData();
        }).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            writeData();
        }).start();


    }

    static PipedReader reader = new PipedReader();
    static PipedWriter writer = new PipedWriter();

    private static void writeData() {
        try {
            System.out.println("write: ");
            for (int i = 1; i <= 300; i++) {
                String outString = String.valueOf(i);
                writer.write(outString);
                System.out.println(outString);
            }
            System.out.println();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readData() {
        try {
            char[] byteArr = new char[20];
            int readLength = reader.read(byteArr);
            while (readLength != -1) {
                String newData = new String(byteArr, 0, readLength);
                System.out.println(newData);
                readLength = reader.read(byteArr);
            }
            System.out.println();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
