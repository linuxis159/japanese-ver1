package com.study.japanese.controller.api;

import com.study.japanese.config.auth.PrincipalDetail;
import com.study.japanese.dto.CommentDto;
import com.study.japanese.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class CommentApiController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @PostMapping("/auth/comment/write")
    public CommentDto.Response writeComment(
            @RequestBody CommentDto.WritingRequest writingRequest,
            @AuthenticationPrincipal PrincipalDetail curUser){
        writingRequest.setUser(curUser.getUser());
        CommentDto.Response resCommentDto = commentService.writeComment(writingRequest);
        return resCommentDto;
    }
    @DeleteMapping("auth/comment/delete/{commentId}")
    public void deleteComment(@PathVariable int commentId){
        commentService.deleteComment(commentId);

    }
}
