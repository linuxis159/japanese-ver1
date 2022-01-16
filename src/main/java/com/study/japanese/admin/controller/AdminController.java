package com.study.japanese.admin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("")
    String adminForm(){
        return "admin/admin";
    }
    @GetMapping("/addCategry")
    String addCategoryForm(){
        return "admin/addCategory";
    }



}
