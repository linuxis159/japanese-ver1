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
    @GetMapping("/category/add")
    String addCategoryForm(){
        return "admin/addCategory";
    }

    @GetMapping("/analysis")
    String analysis(){
        return "admin/analysis";
    }




}
