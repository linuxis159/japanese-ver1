package com.study.japanese.admin.controller;

import com.study.japanese.admin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;



}
