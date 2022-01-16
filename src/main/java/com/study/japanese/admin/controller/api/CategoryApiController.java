package com.study.japanese.admin.controller.api;

import com.study.japanese.admin.service.CategoryService;
import com.study.japanese.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admim")
public class CategoryApiController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/addBaordForm")
    public List<Category> getAllCategory(){
        List<Category> categories = new ArrayList();
        categories = categoryService.getAllCategory();
        return categories;
    }



}
