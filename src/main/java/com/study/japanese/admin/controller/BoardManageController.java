package com.study.japanese.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/board")
public class BoardManageController {
    @GetMapping("")
    String boardManageForm(){
        return "admin/boardManage";
    }
}
