package com.mhua.adminbackend.exception;

public class BaseException extends RuntimeException {

    private final int statusCode;

    // 默认状态码构造方法
    public BaseException(String message) {
        this(message, 500); // 默认设置为 500（或其他你定义的通用错误码）
    }

    // 原有带状态码的构造方法
    public BaseException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
