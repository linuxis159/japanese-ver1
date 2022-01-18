package com.study.japanese.admin.controller;

import com.study.japanese.dto.UserDto;
import com.study.japanese.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserManageController {
    private final UserService userService;

    @GetMapping("/user/manage")
    String  userManageForm(Model model) {
        List<UserDto> users = userService.getAllUser();
        model.addAttribute("users",users);
        return "admin/userManage";
    }
}
