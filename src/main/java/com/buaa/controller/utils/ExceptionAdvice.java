package com.buaa.controller.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler
    public R exceptionHandle(Exception e) {
        e.printStackTrace();
        return new R(false, "服务器异常，请稍后再试");
    }
}
