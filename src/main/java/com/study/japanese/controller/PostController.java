package com.study.japanese.controller;

import com.study.japanese.security.PrincipalDetail;
import com.study.japanese.dto.PostDto;
import com.study.japanese.repository.PostRepository;
import com.study.japanese.service.BoardService;
import com.study.japanese.service.PostService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.study.japanese.constraint.Constants.Post.YOUTUBE_LINK_PATTERN;
import static com.study.japanese.constraint.Constants.Post.YOUTUBE_EMBED_PATTERN;


@Controller
@RequestMapping("/board")
public class PostController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(PostController.class);



    @GetMapping("/list/{id}")
    public String getPosts(
            @PathVariable("id") int id,
            @PageableDefault(size = 15, sort = "createdDate",
                    direction = Sort.Direction.DESC)
                    Pageable pageable, Model model ) {

        PostDto.PagingPosts resPosts = postService.getPagingPostsByBoard(id,pageable);

        model.addAttribute("sortedPosts",resPosts);
        return "post/postList";
    }

    @GetMapping("/view/{id}")
    public String getPostView(
            @PathVariable("id") int id, Model model,
            @AuthenticationPrincipal PrincipalDetail curUser) {

        PostDto.WritingResponse resPostDto = postService.getPost(id);

        //유튜브영상주소 삽입시 페이지에서 영상 재생가능하도록 문자열 처리
        String replacedContnet = replaceYoutubeLink(resPostDto.getContent());
        resPostDto.setContent(replacedContnet);

        model.addAttribute("post", resPostDto);

        if(curUser != null)
            model.addAttribute("currentUser",curUser.getUsername());
        return "post/postView";
    }

    @GetMapping("/auth/post/write/{id}")
    public String writeForm(
            @PathVariable int id,
            PostDto.WritingRequest writingRequest, Model model) {
        model.addAttribute("boardId",id);
        return "post/writePost";
    }

    @PostMapping("/auth/post/write/{id}")
    public String writePost(@PathVariable int id,
            @Validated PostDto.WritingRequest writingRequest,
            BindingResult bindingResult,
            @AuthenticationPrincipal PrincipalDetail curUser,
            Model model) {
        model.addAttribute("boardId",id);
        if(bindingResult.hasErrors())
            return "post/writePost";
        logger.info("resPost:"+writingRequest.toString());
        writingRequest.setUser(curUser.getUser());
        PostDto.WritingResponse resPost = postService.writePost(writingRequest);
        model.addAttribute("post", resPost);
        return "redirect:/board/view/"+String.valueOf(resPost.getId());
    }


    @GetMapping("/auth/post/update/{id}")
    public String getUpdatePostForm(
            @PathVariable int id,
            PostDto.UpdateRequest updateRequest,
            Model model,
            @AuthenticationPrincipal PrincipalDetail curUser){
        if(!authCheck(id, curUser.getUser().getId())){
            return "errors/authError";
        }
        PostDto.WritingResponse resPost = postService.getPost(id);
        model.addAttribute("post",resPost);
        return "post/updatePost";
    }

    @PostMapping("/auth/post/update/{id}")
    public String updatePost(
            @PathVariable int id,
            @Validated PostDto.UpdateRequest updateRequest,
            BindingResult bindingResult,
            Model model,
            @AuthenticationPrincipal PrincipalDetail curUser){
        if(!authCheck(updateRequest.getId(), curUser.getUser().getId()))
            return "errors/authError";
        if(bindingResult.hasErrors()) {
            model.addAttribute("post",updateRequest);
            return "post/updatePost";
        }

        PostDto.WritingResponse resPost = postService.updatePost(updateRequest);
        model.addAttribute("post",resPost);
        return "redirect:/board/view/"+resPost.getId();
    }


    //유튜브 영상주소 문자열처리
    String replaceYoutubeLink(String content) {
        if (content.contains(YOUTUBE_LINK_PATTERN)) {
            String youtubeLink;
            String replecedYoutueLink;
            String embedYoutubeLink;
            for (int i = 0; i < content.length(); i++) {
                if (content.indexOf("h") != -1) {
                    content.substring(i, YOUTUBE_LINK_PATTERN.length() + i).equals(YOUTUBE_LINK_PATTERN);
                    youtubeLink = content.substring(i, YOUTUBE_LINK_PATTERN.length() + i + 11);
                    embedYoutubeLink = YOUTUBE_EMBED_PATTERN + content.substring(i+YOUTUBE_LINK_PATTERN.length(),i+YOUTUBE_LINK_PATTERN.length()+11);
                    replecedYoutueLink = "<div>" +
                            "<div>" +
                            "<embed src=\"" + embedYoutubeLink +"?version3" + "\" width=\"560\" height=\"315\" allowfullscreen=\"true\">" +
                            "</div><a href=\"" + youtubeLink + "\" target=\"_blank\">" +
                            youtubeLink + "</a></div>";
                    content.replace(content.substring(i, YOUTUBE_LINK_PATTERN.length() + i + 10), replecedYoutueLink);
                    return replecedYoutueLink;
                }
            }
        }
        return content;
    }
    boolean authCheck(int id, String curUserId){
        String writerId = postRepository.findById(id).get().getUser().getId();
        return writerId.equals(curUserId);
    }
}