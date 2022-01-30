package com.study.japanese.controller;

import com.study.japanese.dto.PostDto;
import com.study.japanese.entity.Post;
import com.study.japanese.repository.PostRepository;
import com.study.japanese.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController  {
    private final PostService postService;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(MainController.class);
    @GetMapping(value = {"/main","/"})
    String mainForm(
            @RequestParam(value = "query",required = false) String word, Model model,
            @PageableDefault(size=10, sort="createdDate", direction=Sort.Direction.DESC)Pageable pageable){
        model.addAttribute("placeholder","통합검색");
        if(word != null){
            List<Post> posts = postRepository.findAll();
            List<PostDto.PostListRow> searchingPosts = posts.stream()
                    .filter(post -> post.getTitle().contains(word))
                    .map(post -> modelMapper.map(post,PostDto.PostListRow.class))
                    .collect(Collectors.toList());
            int start = (int)pageable.getOffset();
            int end = (start + pageable.getPageSize()) > searchingPosts.size() ? searchingPosts.size() : (start + pageable.getPageSize());
            Page<PostDto.PostListRow> pagingAllPosts =
                    new PageImpl(searchingPosts.subList(start,end),pageable,posts.size());
            PostDto.PagingPosts pagingResultPosts = new PostDto.PagingPosts();
            pagingResultPosts.setVariable(pagingAllPosts, pageable);
            model.addAttribute("sortedPosts",pagingResultPosts);
            return "post/postList";

        }
        else {
            Page<Post> posts = postService.getAllPost(pageable);
            Page<PostDto.LatestPost> postPagingDto =
                    posts.map(post -> modelMapper.map(post, PostDto.LatestPost.class));
            model.addAttribute("allPost", postPagingDto);
            return "main";
        }
    }
}
