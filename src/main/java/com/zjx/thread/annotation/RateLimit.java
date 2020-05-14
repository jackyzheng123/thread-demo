package com.zjx.thread.annotation;

import java.lang.annotation.*;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2020/5/13 17:27
 * @Version V1.0
 **/
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    double limitNum() default 20;  //默认每秒放入桶中的令牌

}