package com.zjx.thread.jvm;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description 方法区OOM
 *
 * 设置方法区内存大小：
 * -XX:PermSize=10M
 * -XX:MaxPermSize=10M
 *
 * 借助cglig直接操作字节码运行时生成大量的动态类
 *
 * @Author Carson Cheng
 * @Date 2019/3/19 17:35
 * @Version V1.0
 **/
public class JavaMethodAreaOOM {

    public static void main (String[] args){

        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invoke(o, objects);
                }
            });
            enhancer.create();
        }
    }

    static class OOMObject{

    }
}
