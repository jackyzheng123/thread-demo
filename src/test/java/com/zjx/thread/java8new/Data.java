package com.zjx.thread.java8new;

import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2019/3/8 15:24
 * @Version V1.0
 **/
public class Data {

    private static final List<Person> list;

    static {
        Person person1 = new Person("宋江", 40, "男");
        Person person2 = new Person("李逵", 20, "男");
        Person person3 = new Person("武松", 30, "男");
        Person person4 = new Person("林冲", 36, "男");
        Person person5 = new Person("扈十娘", 35, "女");
        Person person6 = new Person("鲁智深", 46, "男");
        Person person7 = new Person("杜二娘", 18, "女");

        list = Arrays.asList(person1, person2, person3, person4, person5, person6, person7);
    }

    public static List<Person> getPersonList() {
        return list;
    }
}
