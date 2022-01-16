package com.study.japanese.controller.api;


import com.study.japanese.dto.Code;
import com.study.japanese.dto.EmailDto;
import com.study.japanese.dto.UserDto;
import com.study.japanese.entity.User;
import com.study.japanese.service.EmailService;
import com.study.japanese.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.study.japanese.constraint.Constants.Auth.ALREADY_JOINED;
import static com.study.japanese.constraint.Constants.User.NOT_EQUAL_PASS;
@RestController
@RequestMapping("/user")
public class UserApiController {

    private final EmailService emailService;
    private final UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserApiController.class);

    public UserApiController(EmailService emailService,
                             UserService userService){
        this.emailService = emailService;
        this.userService = userService;
    }

        @PostMapping("/join")
        public UserDto.JoinResponse join(
                @RequestBody @Validated UserDto.JoinRequest joinRequest,
                BindingResult bindingResult,
                HttpServletRequest request){
            UserDto.JoinResponse joinResponse = new UserDto.JoinResponse();
            HttpSession session = request.getSession();
            String code = "empty";

            if(session.getAttribute("joinCode")!=null)
                code = (String)session.getAttribute("joinCode");

            if(bindingResult.hasErrors()){
                joinResponse.setErrors(bindingResult.getAllErrors());
                return joinResponse;
            }

            if(userService.idCheck(joinRequest.getId()) == ALREADY_JOINED){
                System.out.println("IDCHECK:");
                bindingResult.addError(new FieldError("userDto","username","중복된 ID입니다"));
                joinResponse.setErrors(bindingResult.getAllErrors());
                return joinResponse;
            }

            if(!joinRequest.getPassword().equals(joinRequest.getPasswordConfirm())){
                bindingResult.addError(new FieldError("userDto","passwordConfirm",NOT_EQUAL_PASS));
                joinResponse.setErrors(bindingResult.getAllErrors());
                return joinResponse;
            }

            if(!(code.equals(joinRequest.getCode()))){
                bindingResult.addError(new FieldError("userDto","email","이메일인증이 제대로 되지 않았습니다"));
                joinResponse.setErrors(bindingResult.getAllErrors());
                return joinResponse;
            }

            userService.join(joinRequest);

        return joinResponse;
    }
        @PostMapping("/join/id/check")
        public int idCheck(
                @RequestBody User user){
            return userService.idCheck(user.getId());
        }

        @PostMapping("/join/email/send")
        public int sendJoinEmail(@RequestBody EmailDto email, HttpServletRequest request) throws Exception {
            int check = emailService.alreadyJoinedEmailCheck(email.getEmail());
            if(check == ALREADY_JOINED)
                return ALREADY_JOINED;
            else {
                HttpSession session = request.getSession();
                emailService.sendSimpleMessage(email.getEmail());
                session.setAttribute("joinCode",emailService.ePw);
                return 0;
            }
        }

        @PostMapping("/join/code/check")
        public Code checkJoinCode(@RequestBody Code code,HttpServletRequest request){
            HttpSession session = request.getSession();
            String emailCode = (String)session.getAttribute("joinCode");
            logger.info("Code:"+code.getCode());
            if(emailCode.equals(code.getCode())){
                logger.info("Code:"+code.getCode());
                code.setResult(1);
                return code;
            }
            else {
                code.setResult(0);
                code.setCode("Error");
                return code;
            }
        }

        @PostMapping("/findId/email/send")
        public int sendFindIdEmail(
                @RequestBody EmailDto email,
                HttpServletRequest request) throws Exception {
            int check = emailService.alreadyJoinedEmailCheck(email.getEmail());

            if(check == ALREADY_JOINED){
                HttpSession session = request.getSession();
                emailService.sendSimpleMessage(email.getEmail());
                session.setAttribute("userFoundId",userService.getUserIdByEmail(email.getEmail()));
                session.setAttribute("findIdCode",emailService.ePw);
                return ALREADY_JOINED;
            }

            else{
                return 0;
            }
        }
        @PostMapping("/findId/code/check")
        public Code checkFindIdCode(
                @RequestBody Code code,
                HttpServletRequest request){
            HttpSession session = request.getSession();
            String emailCode = (String)session.getAttribute("findIdCode");
            if(emailCode.equals(code.getCode())){
                code.setResult(1);
                code.setSessionId(session.getId());
                return code;
            }
            else {
                code.setResult(0);
                code.setCode("Error");
                return code;
            }

        }
        @PostMapping("/findPass/code/send")
        public int findPassCodeSend(
                @RequestBody UserDto.UserPassFindRequest userPassFindRequest,
                HttpServletRequest request) throws Exception {
            if(userService.idCheck(userPassFindRequest.getUserId()) == ALREADY_JOINED){
                String email = userService.getEmailByUserId(userPassFindRequest.getUserId());
                emailService.sendSimpleMessage(email);
                HttpSession session = request.getSession();
                session.setAttribute("findPassCode",emailService.ePw);
                return 1;
            }
            else
                return 0;
        }

    @PostMapping("/findPass/code/check")
    public Code checkResetPassCode(
            @RequestBody Code code,
            HttpServletRequest request){
        HttpSession session = request.getSession();
        String emailCode = (String)session.getAttribute("findPassCode");
        if(emailCode.equals(code.getCode())){
            code.setResult(1);
            code.setSessionId(session.getId());
            return code;
        }
        else {
            code.setResult(0);
            code.setCode("Error");
            return code;
        }

    }

}
