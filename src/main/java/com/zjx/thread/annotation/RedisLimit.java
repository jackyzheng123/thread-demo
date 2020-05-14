package com.zjx.thread.annotation;

import com.zjx.thread.enums.LimitType;

import java.lang.annotation.*;

/**
 * @Description 限流
 * @Author Carson Cheng
 * @Date 2020/5/14 9:36
 * @Version V1.0
 *
 * 参考：https://blog.csdn.net/johnf_nash/article/details/89791808?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisLimit {

    /**
     * 资源的名称
     * @return
     */
    String name() default "";

    /**
     * 资源的key
     *
     * @return
     */
    String key() default "";

    /**
     * Key的prefix
     *
     * @return
     */
    String prefix() default "";

    /**
     * 给定的时间段
     * 单位秒
     *
     * @return
     */
    int period();

    /**
     * 最多的访问限制次数
     *
     * @return
     */
    int count();

    /**
     * 类型
     *
     * @return
     */
    LimitType limitType() default LimitType.CUSTOMER;
}