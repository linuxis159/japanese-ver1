package com.study.japanese.controller;

import com.study.japanese.dto.PostDto;
import com.study.japanese.entity.Post;
import com.study.japanese.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private PostService postService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = {"/main","/"})
    String mainForm(Model model, @PageableDefault(size=10,
            sort="createdDate", direction=Sort.Direction.DESC)Pageable pageable){

        Page<Post> pagingPosts = postService.getAllPost(pageable);
        Page<PostDto.WritingResponse> postPagingDTO =  pagingPosts.map(post -> modelMapper.map(post, PostDto.WritingResponse.class));
        model.addAttribute("allPost",postPagingDTO);
        return "main";
    }
}
