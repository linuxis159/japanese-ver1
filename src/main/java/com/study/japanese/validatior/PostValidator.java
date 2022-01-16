//package com.study.japanese.validatior;
//
//
//import com.study.japanese.config.auth.PrincipalDetail;
//import com.study.japanese.dto.PostDto;
//import com.study.japanese.repository.PostRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.annotation.AccessType;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//
//public class PostValidator implements Validator {
//    @Autowired
//    PostRepository postRepository;
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return PostDto.UpdateRequest.class.equals(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//
//
//    }
//}
