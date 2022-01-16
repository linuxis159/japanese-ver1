package com.study.japanese.controller.api;

import com.study.japanese.config.auth.PrincipalDetail;
import com.study.japanese.dto.Code;
import com.study.japanese.dto.UpdateEmailDTO;
import com.study.japanese.dto.UpdatedUserNameDTO;
import com.study.japanese.entity.User;
import com.study.japanese.service.EmailService;
import com.study.japanese.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class AuthUserApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @PutMapping("/auth/info/name/update/{name}")
    public UpdatedUserNameDTO updataName(
            @PathVariable String name, @AuthenticationPrincipal PrincipalDetail curUser){
        userService.updateName(curUser.getUsername(), name);
        UpdatedUserNameDTO updatedUserNameDTO = new UpdatedUserNameDTO();
        updatedUserNameDTO.setName(name);
        return updatedUserNameDTO;
    }

    @PostMapping("/auth/info/email/check/{email}")
    public int emailCheckEmail(@PathVariable String email,
                               @AuthenticationPrincipal PrincipalDetail principal
                                , HttpServletRequest request) throws Exception {

        int check = emailService.alreadyJoinedEmailCheck(email);
        if(check == 1)
            return 1;
        else {
            try {
                HttpSession session = request.getSession();
                emailService.sendSimpleMessage(email);
                session.setAttribute("user",emailService.ePw);
                System.out.println("codeValue:"+emailService.ePw);
            } catch(MailException es){
                es.printStackTrace();
                return -1;
            }
        }
        return 2;
    }

    @PostMapping("/auth/info/code/check/{code}")
    public Code codeCheck(@PathVariable String code,HttpServletRequest request){
        HttpSession session = request.getSession();
        String epw = (String)session.getAttribute("user");
        System.out.println("codeValue:"+epw);

        Code codeDTO = new Code();
        if(epw.equals(code)){
            codeDTO.setResult(1);
            codeDTO.setCode(code);
            return codeDTO;
        }
        else {
            codeDTO.setResult(0);
            codeDTO.setCode("Error");
            return codeDTO;
        }
    }

    @PutMapping("/auth/info/email/update")
    int updateEmail(@RequestBody UpdateEmailDTO dto, @AuthenticationPrincipal PrincipalDetail principal,
                    HttpServletRequest request){
        HttpSession session = request.getSession();
        String code = (String)session.getAttribute("user");
        if(dto.getCode().equals(code)){
            userService.updateEmail(dto.getEmail(), principal.getUser());
            session.removeAttribute("user");
            return 1;
        }
        else
            return 0;

    }

}
