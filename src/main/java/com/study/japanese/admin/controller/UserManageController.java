package com.study.japanese.admin.controller;

import com.study.japanese.dto.EmailDto;
import com.study.japanese.dto.EmailMessageDto;
import com.study.japanese.dto.UserDto;
import com.study.japanese.service.EmailService;
import com.study.japanese.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserManageController {
    private final UserService userService;
    private final EmailService emailService;

    @GetMapping(value={"","/list"})
    String  userManageForm(Model model) {
        List<UserDto> users = userService.getAllUser();
        model.addAttribute("users",users);
        return "admin/user/userManage";
    }

    @GetMapping("/mail/send")
    String  emailSendAllUserForm(Model model, EmailMessageDto emailMessageDto) {
        return "admin/user/emailSendAllUser";
    }

    @PostMapping("/mail/send")
    String  emailSendAllUser(Model model, @Validated EmailMessageDto emailMessageDto,
                              BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            return "admin/user/emailSendAllUser";
        }

        List<UserDto> users = userService.getAllUser();
        emailService.sendEmailMessages(users.stream()
                .map( user-> {
                    return user.getEmail();
                }).collect(Collectors.toList()),emailMessageDto);

        model.addAttribute("users",users);
        return "admin/user/userManage";
    }
}
