package com.study.japanese.service;


import com.study.japanese.dto.PostDto;
import com.study.japanese.entity.Comment;
import com.study.japanese.entity.Post;
import com.study.japanese.repository.BoardRepository;
import com.study.japanese.repository.CommentRepository;
import com.study.japanese.repository.PostRepository;
import com.study.japanese.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static com.study.japanese.constraint.Constants.Exception.*;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(PostService.class);




    public PostDto.PagingPosts getPagingPostsByBoard(int id, Pageable pageable){
        Page<Post> posts = postRepository.findByBoard_Id(id, pageable)
                .orElseThrow(() -> new NullPointerException());

        Page<PostDto.PostListRow> pagingPostDtos =
                posts.map(post -> modelMapper.map(post, PostDto.PostListRow.class));

        PostDto.PagingPosts resPosts = new PostDto.PagingPosts();

        resPosts.setPosts(pagingPostDtos);
        resPosts.setBoardId(id);
        resPosts.setSort(pageable
                .getSort()
                .toList()
                .get(0)
                .getProperty());
        resPosts.setOrder(pageable
                .getSort()
                .toList()
                .get(0)
                .getDirection()
                .toString());
        return resPosts;

    }

    public PostDto.PagingPosts getAllPagingPost(Pageable pageable){
        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostDto.PostListRow> pagingPostDtos =
                posts.map(post -> modelMapper.map(post, PostDto.PostListRow.class));

        PostDto.PagingPosts resPosts = new PostDto.PagingPosts();

        resPosts.setPosts(pagingPostDtos);
        resPosts.setSort(pageable
                .getSort()
                .toList()
                .get(0)
                .getProperty());
        resPosts.setOrder(pageable
                .getSort()
                .toList()
                .get(0)
                .getDirection()
                .toString());
        return resPosts;

    }

    public Page<Post> getAllPost(Pageable pageable){
        Page<Post> posts = postRepository.findAll(pageable);
        return posts;
    }

    @Transactional
    public PostDto.WritingResponse getPost(int id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND_EXCEPTION_MESSAGE));
        post.viewCountUp();
        PostDto.WritingResponse resPostDto =
                modelMapper.map(post,PostDto.WritingResponse.class);
        Collections.sort(resPostDto.getComments());
        return resPostDto;
    }
    public List<PostDto.WritingResponse> getPostsByUserId(String userId) {
        List<Post> posts = postRepository.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND_EXCEPTION_MESSAGE));

        List<PostDto.WritingResponse> resPostDtos = posts.stream()
                .map(post -> modelMapper.map(post,PostDto.WritingResponse.class))
                .collect(Collectors.toList());

        return resPostDtos;
    }

    public Set<PostDto.UserCommentedPostRow> getPostsByUserCommented(String userId){
        List<Comment> comments = commentRepository.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND_EXCEPTION_MESSAGE));

        Set<PostDto.UserCommentedPostRow> resPostDtos = comments.stream()
                .map(comment -> modelMapper.map(comment.getPost(),PostDto.UserCommentedPostRow.class))
                .collect(Collectors.toSet());
        return resPostDtos;
    }

    @Transactional
    public PostDto.WritingResponse writePost(PostDto.WritingRequest writingRequest){
        writingRequest.setBoard(boardRepository.findById(writingRequest.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException(BOARD_NOT_FOUND_EXCEPTION_MESSAGE)));
        logger.info("request:"+writingRequest.toString());
        Post post = modelMapper.map(writingRequest,Post.class);
        logger.info("post:"+post.toString());
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.WritingResponse.class);

    }
    @Transactional
    public PostDto.WritingResponse updatePost(PostDto.UpdateRequest updateRequest){

        Post post = postRepository.findById(updateRequest.getId())
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND_EXCEPTION_MESSAGE));
        post.updateTitle(updateRequest.getTitle());
        post.updaContent(updateRequest.getContent());
        postRepository.save(post);
        return modelMapper.map(post, PostDto.WritingResponse.class);

    }


    public void deletePost(int targetId) {
        postRepository.deleteById(targetId);
    }
}
