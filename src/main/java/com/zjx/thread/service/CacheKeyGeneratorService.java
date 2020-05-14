package com.zjx.thread.service;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * key生成器
 *
 * @author Levin
 * @date 2018/03/22
 */
public interface CacheKeyGeneratorService {

    /**
     * 获取AOP参数,生成指定缓存Key
     *
     * @param jointPoint
     * @return 缓存KEY
     */
    String getLockKey(ProceedingJoinPoint jointPoint);

}