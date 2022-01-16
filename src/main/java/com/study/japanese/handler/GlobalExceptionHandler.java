package com.study.japanese.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(value = NullPointerException.class)
    public String handleNullPointException(
            NullPointerException e){
        return "<h1>"+e.getMessage()+"</h1>";
    }
}
