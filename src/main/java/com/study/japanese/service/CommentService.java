package com.study.japanese.service;

import com.study.japanese.dto.CommentDto;
import com.study.japanese.entity.Comment;
import com.study.japanese.repository.CommentRepository;
import com.study.japanese.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.study.japanese.constraint.Constants.Exception.COMMENT_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.study.japanese.constraint.Constants.Exception.POST_NOT_FOUND_EXCEPTION_MESSAGE;

@Service
public class CommentService {
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(CommentService.class);

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          ModelMapper modelMapper){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    public CommentDto.Response writeComment(CommentDto.WritingRequest writingRequest){
        writingRequest.setPost(postRepository.findById(writingRequest.getPostId())
                .orElseThrow(()->new EntityNotFoundException(POST_NOT_FOUND_EXCEPTION_MESSAGE)));

        Comment comment = modelMapper.map(writingRequest,Comment.class);
        logger.info("COMMENT:"+comment);

        Comment savedComment = commentRepository.save(comment);
        logger.info("SAVED_COMMENT:"+savedComment);

        CommentDto.Response resCommentDto = modelMapper.map(savedComment,CommentDto.Response.class);
        return resCommentDto;
    }
    public List<CommentDto.Response> getCommentsByUserId(String userId){
        List<Comment> comments = commentRepository.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND_EXCEPTION_MESSAGE));

        List<CommentDto.Response> resCommentDtos = comments.stream()
                .distinct()
                .map(comment -> modelMapper.map(comment,CommentDto.Response.class))
                .collect(Collectors.toList());
        return resCommentDtos;

    }

    public void deleteComment(int commentId){
        commentRepository.deleteById(commentId);
    }

}
