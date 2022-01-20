package com.study.japanese.admin.controller;

import com.study.japanese.dto.PostDto;
import com.study.japanese.entity.Post;
import com.study.japanese.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/post")
public class PostManageController {
    private final PostService postService;

    @GetMapping("")
    String postManageForm(Model model,@PageableDefault(size = 15, sort = "createdDate",
            direction = Sort.Direction.DESC)
            Pageable pageable){
        PostDto.PagingPosts resPosts = postService.getAllPagingPost(pageable);
        model.addAttribute("sortedPosts",resPosts);
        return "admin/postManage";

    }
}
