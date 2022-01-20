package com.study.japanese.admin.controller.api;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AnanysisController {
    @GetMapping("/analysis")
    String analysis(){
        return "admin/analysis";

    }

}
