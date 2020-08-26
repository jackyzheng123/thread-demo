package com.zjx.thread.demo1;

import lombok.ToString;

import java.util.*;

/**
 * final域不可变？ 引用不可变，对象可变
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/8/25 16:36
 * @Version V1.0
 **/
@ToString
public class MagicNumber {

    private static final int num = 0;
    private static final String str = "hello";
    private static final Map<Integer, String> map = new HashMap<>();
    private static final Set<String> set = new HashSet<>();


    public static void main(String[] args) {
        map.put(1, "zzz");
        map.put(3, "jjj");
        map.put(5, "xxx");
        map.put(7, "777");
        map.put(9, "999");

        set.add("111");
        set.add("222");
        set.add("333");
        set.add("444");

        //num = 10; // Cannot assign a value to final variable 'num'

        //str += "world"; // Cannot assign a value to final variable 'str'

        map.forEach((key, value) -> System.out.println(key + "=" + value));

        set.forEach(v->System.out.println(v));

        final Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
