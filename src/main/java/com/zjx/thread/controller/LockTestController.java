package com.zjx.thread.controller;

import com.zjx.thread.annotation.CacheLock;
import com.zjx.thread.annotation.CacheParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2020/5/14 11:52
 * @Version V1.0
 **/
@RestController
public class LockTestController {

    /**
     * 测试重复提交
     * @param token
     * @return
     */
    @CacheLock(prefix = "books")
    @GetMapping("/lock")
    public String query(@CacheParam(name = "token") @RequestParam String token) {
        System.out.println("提交了");
        return "success - " + token;
    }

}
