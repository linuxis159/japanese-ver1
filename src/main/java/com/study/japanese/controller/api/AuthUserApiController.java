package com.study.japanese.controller.api;

import com.study.japanese.security.PrincipalDetail;
import com.study.japanese.dto.UpdateEmailDTO;
import com.study.japanese.dto.UpdatedUserNameDTO;
import com.study.japanese.entity.Code;
import com.study.japanese.repository.CodeRedisRepository;
import com.study.japanese.service.EmailService;
import com.study.japanese.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class AuthUserApiController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final EmailService emailService;
    @Autowired
    private final CodeRedisRepository codeRedisRepository;


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
                emailService.sendSimpleMessage(email);
            } catch(MailException es){
                es.printStackTrace();
                return -1;
            }
        }
        return 2;
    }

    @PostMapping("/auth/info/code/check/{code}")
    public Code codeCheck(@PathVariable String code){

        Code savedCode = codeRedisRepository.findById(code).orElseGet(() -> new Code("Not Found",0));
        return savedCode;
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
