package com.study.japanese.service;

import com.study.japanese.dto.UserDto;
import com.study.japanese.entity.User;
import com.study.japanese.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static com.study.japanese.constraint.Constants.Auth.ALREADY_JOINED;

import static com.study.japanese.constraint.Constants.Exception.USER_NOT_FOUND_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encodeer;
    private final ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(UserService.class);




    public List<UserDto> getAllUser(){
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user,UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto.InfoResponse getUserInfo(String userId){
        User user = userRepository.findById(userId).get();
        UserDto.InfoResponse infoResponse = modelMapper.map(user,UserDto.InfoResponse.class);
        return infoResponse;

    }
    public String getUserIdByEmail(String email){
        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE));
        return user.getId();

    }
    public String getEmailByUserId(String userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE));
        return user.getEmail();
    }


    public void join(UserDto.JoinRequest joinRequest){
        String encPassword = encodeer.encode(joinRequest.getPassword());
        joinRequest.setPassword(encPassword);
        logger.info("USER_JOIN_REQUEST:"+joinRequest);
        User user = modelMapper.map(joinRequest,User.class);
        logger.info("USER:"+user);
        userRepository.save(user);
    }

    public int idCheck(String userId){
        int checkResult = 0;
        try {
            if(userRepository.findById(userId).isPresent())
                checkResult = ALREADY_JOINED;
        }

        catch (NullPointerException e){
            System.out.println("NULL값");
        }
        return checkResult;
    }
    @Transactional
    public void updateName(String userId, String name) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE));
        user.updateUserName(name);
    }
    @Transactional
    public void updateEmail(String email, User user) {
        user.updateUserEmail(email);
    }

    @Transactional
    public void deleteUser(String username){
        userRepository.deleteById(username);
    }
}
