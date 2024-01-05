package com.example.ntb_springboot.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex) {
        // 你可以在这里记录异常信息或者返回一个友好的错误消息
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}