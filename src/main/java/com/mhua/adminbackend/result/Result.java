package com.mhua.adminbackend.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    /**
     * 状态码
     * 200 成功
     * 401 登录失败
     * 500 错误
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 200;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 200;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 500;
        return result;
    }

}
