package com.study.japanese.service;

import com.study.japanese.dto.RecommendDto;
import com.study.japanese.entity.Post;
import com.study.japanese.entity.Recommend;
import com.study.japanese.entity.User;
import com.study.japanese.repository.PostRepository;
import com.study.japanese.repository.RecommendRepository;
import com.study.japanese.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static com.study.japanese.constraint.Constants.Exception.POST_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.study.japanese.constraint.Constants.Exception.USER_NOT_FOUND_EXCEPTION_MESSAGE;


@Service
public class RecommendService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private RecommendRepository recommendRepository;
    private ModelMapper modelMapper;

    private Logger logger = LoggerFactory.getLogger(RecommendService.class);


    public RecommendService(PostRepository postRepository,
                            UserRepository userRepository,
                            RecommendRepository recommendRepository,
                            ModelMapper modelMapper){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.recommendRepository = recommendRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public RecommendDto.Response recommend(int postId, String username){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND_EXCEPTION_MESSAGE));
        User curUser = userRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE));
        RecommendDto.Request request = new RecommendDto.Request();
        request.setPost(post);
        request.setUser(curUser);

        Recommend recommend = modelMapper.map(request,Recommend.class);
        recommendRepository.save(recommend);
        postRepository.save(post);


        RecommendDto.Response response = new RecommendDto.Response();
        response.setRecommendCount(recommendRepository.countByPostId(postId));
        response.setResultState(1);
        return response;


    }
}
