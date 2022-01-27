package com.study.japanese.controller;

import com.study.japanese.dto.PostDto;
import com.study.japanese.entity.Post;
import com.study.japanese.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController  {
    private final PostService postService;
    private final ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(MainController.class);
    @GetMapping(value = {"/main","/"})
    String mainForm(Model model, @PageableDefault(size=10,
            sort="createdDate", direction=Sort.Direction.DESC)Pageable pageable){

        Page<Post> pagingPosts = postService.getAllPost(pageable);
        Page<PostDto.WritingResponse> postPagingDto =  pagingPosts.map(post -> modelMapper.map(post, PostDto.WritingResponse.class));
        model.addAttribute("allPost",postPagingDto);
        return "main";
    }
}
