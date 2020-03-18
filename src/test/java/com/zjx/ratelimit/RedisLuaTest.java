package com.zjx.ratelimit;

import com.zjx.thread.ThreadStudyApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2020/3/17 17:29
 * @Version V1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ThreadStudyApplication.class)
public class RedisLuaTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void luaTest() {

        DefaultRedisScript<List> script = new DefaultRedisScript<List>();
        script.setResultType(List.class);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/test.lua")));

        /**
         * List设置lua的KEYS
         */
        List<String> keyList = new ArrayList();
        keyList.add("key");
//        keyList.add("value");

        /**
         * 调用脚本并执行
         */
        List result = redisTemplate.execute(script, keyList);
        System.out.println(result);

    }

    @Test
    public void rateLimitLuaTest() {

        DefaultRedisScript<List> script = new DefaultRedisScript<List>();
        script.setResultType(List.class);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/rateLimit.lua")));

        /**
         * List设置lua的KEYS
         */
        List<String> keyList = new ArrayList();
        keyList.add("10");

        /**
         * 用Mpa设置Lua的ARGV[1]
         */
        Map<String,Object> argvMap = new HashMap<String,Object>();
        argvMap.put("limit", "1000");

        /**
         * 调用脚本并执行
         */
        List result = redisTemplate.execute(script, keyList, argvMap);
        System.out.println(result);

    }
}
