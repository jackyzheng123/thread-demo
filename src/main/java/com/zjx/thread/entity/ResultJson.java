package com.zjx.thread.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2020/5/13 17:21
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultJson<T> {

    private Integer code;
    private String msg;
    private T data;

    public static ResultJson ok(Integer code, String msg) {
        ResultJson result = new ResultJson();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
