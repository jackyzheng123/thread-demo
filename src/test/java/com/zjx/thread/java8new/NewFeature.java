package com.zjx.thread.java8new;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Java 8 Stream
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2019/3/8 15:35
 * @Version V1.0
 **/
public class NewFeature {

    /**
     * list.stream().filter()
     * 遍历数据并检查其中的元素时使用。
     * filter接受一个函数作为参数，该函数用Lambda表达式表示。
     * <p>
     * 过滤所有男性, 并且大于30岁
     */
    @Test
    public void filterSexAndAge() {
        List<Person> list = Data.getPersonList();
        List<Person> temp = new ArrayList<>(20);
        // old
        for (Person person : list) {
            if ("男".equals(person.getSex()) && person.getAge() > 30) {
                temp.add(person);
            }
        }
        System.out.println(temp);

        // new 1
        List<Person> collect1 = list.stream()
                .filter(person -> ("男".equals(person.getSex()) && person.getAge() > 30))
                .collect(toList());
        System.out.println(collect1);

        // new 2
        List<Person> collect2 = list.stream()
                .filter(person -> {
                    if ("男".equals(person.getSex()) && person.getAge() > 30) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .collect(toList());
        System.out.println(collect2);
    }


    /**
     * list.stream().map()
     * map生成的是个一对一映射,for的作用
     * <p>
     * 取出所有的用户名字
     */
    @Test
    public void getAllPersonName() {
        List<Person> list = Data.getPersonList();
        List<String> temp = new ArrayList<>(20);

        // old
        for (Person person : list) {
            temp.add(person.getName());
        }
        System.out.println(temp);

        // new 1
        List<String> collect1 = list.stream().map(person -> person.getName()).collect(toList());
        System.out.println(collect1);

        // new 2
        List<String> collect2 = list.stream().map(Person::getName).collect(toList());
        System.out.println(collect2);

        // new 3
        List<String> collect3 = list.stream().map(person -> {
            return person.getName();
        }).collect(toList());
        System.out.println(collect3);
    }

    /**
     * list.stream().flatMap()
     * <p>
     * 顾名思义，跟map差不多,更深层次的操作
     * 但还是有区别的
     * map和flat返回值不同
     * Map 每个输入元素，都按照规则转换成为另外一个元素。
     * 还有一些场景，是一对多映射关系的，这时需要 flatMap。
     * Map一对一
     * Flatmap一对多
     * map和flatMap的方法声明是不一样的
     */
    @Test
    public void flatMapString() {
        List<Person> list = Data.getPersonList();
        //返回类型不一样
        List<String> collect = list.stream()
                .flatMap(person -> Arrays.stream(person.getName().split(" ")))
                .collect(toList());
        System.out.println(collect);

        List<Stream<String>> collect1 = list.stream()
                .map(person -> Arrays.stream(person.getName().split(" ")))
                .collect(toList());
        System.out.println(collect1);

        //用map实现
        List<String> collect2 = list.stream()
                .map(person -> person.getName().split(" "))
                .flatMap(Arrays::stream).collect(toList());
        System.out.println(collect2);

        //另一种方式
        List<String> collect3 = list.stream()
                .map(person -> person.getName().split(" "))
                .flatMap(str -> Arrays.asList(str).stream()).collect(toList());
        System.out.println(collect3);
    }


    /**
     * Reduce
     * <p>
     * 感觉类似递归
     * 数字(字符串)累加
     */
    @Test
    public void reduceTest() {
        //累加，初始化值是 10
        Integer reduce = Stream.of(1, 2, 3, 4)
                .reduce(10, (count, item) -> {
                    System.out.println("count:" + count);
                    System.out.println("item:" + item);
                    return count + item;
                });
        System.out.println(reduce);
        System.out.println("======================");

        Integer reduce1 = Stream.of(1, 2, 3, 4)
                .reduce(0, (x, y) -> x + y);
        System.out.println(reduce1);
        System.out.println("======================");

        String reduce2 = Stream.of("1", "2", "3")
                .reduce("0", (x, y) -> (x + "," + y));
        System.out.println(reduce2);
    }

    /**====================================================================================
     * Collect
     * collect在流中生成列表，map，等常用的数据结构
     * toList()
     * toSet()
     * toMap()
     * 自定义
     * ====================================================================================
     */
    /**
     * toList
     */
    @Test
    public void toListTest(){
        List<Person> data = Data.getPersonList();
        List<String> collect = data.stream()
                .map(Person::getName)
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * toSet
     */
    @Test
    public void toSetTest(){
        List<Person> data = Data.getPersonList();
        Set<String> collect = data.stream()
                .map(Person::getName)
                .collect(Collectors.toSet());
        System.out.println(collect);
    }

    /**
     * toMap
     */
    @Test
    public void toMapTest(){
        List<Person> data = Data.getPersonList();
        Map<String, Integer> collect = data.stream()
                .collect(
                        Collectors.toMap(Person::getName, Person::getAge)
                );
        System.out.println(collect);

        Map<String, String> collect1 = data.stream()
                .collect(Collectors.toMap(per->per.getName(), value->{
                    return value+"1";
                }));
        System.out.println(collect1);
    }

    /**
     * 指定类型
     */
    @Test
    public void toTreeSetTest(){
        List<Person> data = Data.getPersonList();
        TreeSet<Person> collect = data.stream()
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(collect);
    }

    /**
     * 分组
     */
    @Test
    public void toGroupTest(){
        List<Person> data = Data.getPersonList();
        Map<Boolean, List<Person>> collect = data.stream()
                .collect(Collectors.groupingBy(per -> "男".equals(per.getSex())));
        System.out.println(collect);
    }

    /**
     * 分隔
     */
    @Test
    public void toJoiningTest(){
        List<Person> data = Data.getPersonList();
        String collect = data.stream()
                .map(person -> person.getName())
                .collect(Collectors.joining("、", "[", "]"));
        System.out.println(collect);
    }

    /**
     * 自定义
     */
    @Test
    public void reduce(){
        List<String> collect = Stream.of("1", "2", "3").collect(
                Collectors.reducing(new ArrayList<String>(), x -> Arrays.asList(x), (y, z) -> {
                    y.addAll(z);
                    return y;
                }));
        System.out.println(collect);
    }

    /**
     * 调试
     *
     * list.map.fiter.map.xx 为链式调用，最终调用collect(xx)返回结果
     *
     * 分惰性求值和及早求值
     * 通过peek可以查看每个值，同时能继续操作流
     */
    @Test
    public void peekTest() {
        List<Person> data = Data.getPersonList();

        //peek打印出遍历的每个per
        data.stream().map(person->person.getName()).peek(p->{
            System.out.println(p);
        }).collect(toList());
    }
}
