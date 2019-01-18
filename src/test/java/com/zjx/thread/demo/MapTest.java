package com.zjx.thread.demo;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description HashMap Hashtable ConCurrentHashMap
 * @Author Carson Cheng
 * @Date 2019/1/18 12:23
 * @Version V1.0
 **/
public class MapTest {

    public static void main (String[] args){
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new Hashtable<>();
        Map<String, Object> map3 = new ConcurrentHashMap<>();

        map1.put("zjx","zjx");
        map2.put("zjx","zjx");
        map3.put("zjx","zjx");

    }
}
