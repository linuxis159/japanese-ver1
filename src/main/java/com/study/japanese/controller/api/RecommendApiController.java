package com.study.japanese.controller.api;

import com.study.japanese.config.auth.PrincipalDetail;
import com.study.japanese.dto.RecommendDto;
import com.study.japanese.service.PostService;
import com.study.japanese.service.RecommendService;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/board")
@RestController
public class RecommendApiController {

    private final PostService postService;
    private final RecommendService recommendService;

    public RecommendApiController(PostService postService,
                                  RecommendService recommendService){
        this.postService = postService;
        this.recommendService = recommendService;
    }

    @PostMapping("/auth/recommend/vote/{id}")
    public RecommendDto.Response recommend(
            @PathVariable int id,
            @AuthenticationPrincipal PrincipalDetail curUser){
        RecommendDto.Response response = recommendService.recommend(id, curUser.getUsername());
        return response;
    }
}
