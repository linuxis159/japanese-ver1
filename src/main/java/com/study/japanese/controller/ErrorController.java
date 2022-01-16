package com.study.japanese.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private String VIEW_PATH = "/errors/";
    @RequestMapping("/error")
    public String handlerError(HttpServletRequest request, Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
        model.addAttribute("code",status.toString());
        model.addAttribute("msg", httpStatus.getReasonPhrase());
        return VIEW_PATH+"error";
    }
}
