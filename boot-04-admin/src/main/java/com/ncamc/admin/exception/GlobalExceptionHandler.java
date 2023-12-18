package com.ncamc.admin.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 处理全局web controller的异常
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ArithmeticException.class,NumberFormatException.class})
    public String handlerArithmeticException(Exception e){
        log.error("异常是:{}",e);
        return "login";//视图地址
    }
}
