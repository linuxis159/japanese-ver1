package com.study.japanese.admin.controller;

import com.study.japanese.admin.service.CategoryService;
import com.study.japanese.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminBoardController{
    @Autowired
    CategoryService categoryService;

    @GetMapping("/addBoardForm")
    String addBoardForm(Model model){
        List<Category> categories = new ArrayList();
        categories = categoryService.getAllCategory();
        model.addAttribute("categories",categories);

        return "admin/addBoard";
    }





}
