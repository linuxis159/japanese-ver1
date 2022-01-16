package com.study.japanese.controller.api;

import com.study.japanese.service.PostService;
import com.study.japanese.service.RecommendService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/board")
public class PostApiController {
    private final ModelMapper modelMapper;
    private final PostService postService;
    private final RecommendService recommendService;

    public PostApiController(PostService postService,
                             RecommendService recommendService,
                             ModelMapper modelMapper){
        this.postService = postService;
        this.recommendService = recommendService;
        this.modelMapper = modelMapper;
    }

    @DeleteMapping("/auth/post/delete/{postId}")
    public int postDelete(@PathVariable int postId){
        try {
            postService.deletePost(postId);
            return 1;
        } catch (Exception e){
            System.out.println("postDelete Error");
            return 0;

        }
    }




//    @PutMapping("/auth/post/update")
//    public int writeProcess(@RequestBody PostDto.Request request) {
//        postService.updatePost(dto);
//
//
//    }



}
