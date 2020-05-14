package com.zjx.thread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ThreadStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadStudyApplication.class, args);
    }

}

