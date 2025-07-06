package com.mhua.adminbackend.handler;

import com.mhua.adminbackend.exception.BaseException;
import com.mhua.adminbackend.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import  com.mhua.adminbackend.constant.HttpStatusConstant;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
//@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Result<String>> exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        Result<String> result = Result.error(ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(result);
    }
}
