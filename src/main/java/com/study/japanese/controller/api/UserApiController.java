package com.study.japanese.controller.api;


import com.study.japanese.admin.dto.CodeDto;
import com.study.japanese.dto.EmailDto;
import com.study.japanese.dto.UserDto;
import com.study.japanese.entity.Code;
import com.study.japanese.entity.User;
import com.study.japanese.repository.CodeRedisRepository;
import com.study.japanese.service.EmailService;
import com.study.japanese.service.UserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApiController {

    private final EmailService emailService;
    private final UserService userService;
    private final CodeRedisRepository codeRedisRepository;

    private Logger logger = LoggerFactory.getLogger(UserApiController.class);


        @PostMapping("/join")
        public UserDto.JoinResponse join(
                @RequestBody @Validated UserDto.JoinRequest joinRequest,
                BindingResult bindingResult,
                HttpServletRequest request){
            UserDto.JoinResponse joinResponse = new UserDto.JoinResponse();



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

            if(codeRedisRepository.findById(joinRequest.getCode()).isEmpty()){
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
        public int sendJoinEmail(@RequestBody EmailDto email) throws Exception {
            int check = emailService.alreadyJoinedEmailCheck(email.getEmail());
            if(check == ALREADY_JOINED)
                return ALREADY_JOINED;
            else {
                emailService.sendSimpleMessage(email.getEmail());
                return 0;
            }
        }

        @PostMapping("/join/code/check")
        public Code checkJoinCode(@RequestBody CodeDto codeRequest){
            Code savedCode = codeRedisRepository
                    .findById(codeRequest.getCode())
                    .orElseGet(() -> new Code("Not Found",0));

            return savedCode;
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
                @RequestBody String code){
            Code savedCode = codeRedisRepository.findById(code).orElseGet(() -> new Code("Not Found",0));
            return savedCode;
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
            @RequestBody String code,
            HttpServletRequest request){
        Code savedCode = codeRedisRepository.findById(code).orElseGet(() ->new Code("Not Found",0));
        return savedCode;

    }

}
