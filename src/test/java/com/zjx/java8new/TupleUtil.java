package com.zjx.java8new;

/**
 * @Description: 元组辅助类，用于多种类型值的返回, 使用泛型方法实现，利用参数类型推断，编译器可以找出具体的类型
 * @Author Carson Cheng
 * @Date 2019/8/14 14:10
 * @Version V1.0
 **/
public class TupleUtil {

    public static <A, B> TwoTuple<A, B> tuple(A a, B b) {
        return new TwoTuple<A, B>(a, b);
    }

    public static <A, B, C> ThreeTuple<A, B, C> tuple(A a, B b, C c) {
        return new ThreeTuple<A, B, C>(a, b, c);
    }
}

/**
 * 三个元素的元组，用于在一个方法里返回三种类型的值
 * @param <A>
 * @param <B>
 * @param <C>
 */
class ThreeTuple<A, B, C> extends TwoTuple<A, B> {
    public final C third;

    public ThreeTuple(A a, B b, C c) {
        super(a, b);
        this.third = c;
    }
}

/**
 * 两个元素的元组，用于在一个方法里返回两种类型的值
 * @param <A>
 * @param <B>
 */
class TwoTuple<A, B> {
    public final A first;
    public final B second;

    public TwoTuple(A a, B b) {
        this.first = a;
        this.second = b;
    }
}