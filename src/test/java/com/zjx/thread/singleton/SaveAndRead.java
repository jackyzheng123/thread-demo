package com.zjx.thread.singleton;

import java.io.*;

/**
 * 内部类序列化-反序列化的单例模式
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/2 11:59
 * @Version V1.0
 **/
public class SaveAndRead {

    public static void main (String[] args){
        try {
            MyObject myObject = MyObject.getInstance();
            FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\zhengjiaxing\\Desktop\\test.txt"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(myObject);
            oos.close();
            fos.close();
            System.out.println(myObject.hashCode());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fis = new FileInputStream(new File("C:\\Users\\zhengjiaxing\\Desktop\\test.txt"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            final MyObject object = (MyObject) ois.readObject();
            ois.close();
            fis.close();
            System.out.println(object.hashCode());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

class MyObject implements Serializable {

    private static class MyObjectHandler{
        private static MyObject myObject = new MyObject();
    }

    private MyObject(){}

    public static MyObject getInstance(){
        return MyObjectHandler.myObject;
    }

    protected Object readResolve() throws ObjectStreamException{
        System.out.println("调用了readResolve");
        return MyObjectHandler.myObject;
    }
}
