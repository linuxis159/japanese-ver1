package com.study.japanese.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login/loginForm")
    String loginForm() {
        return "login";
    }
}
