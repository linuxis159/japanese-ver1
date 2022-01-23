package com.study.japanese.controller;

import com.study.japanese.security.PrincipalDetail;
import com.study.japanese.dto.PostDto;
import com.study.japanese.dto.UserDto;
import com.study.japanese.service.CommentService;
import com.study.japanese.service.PostService;
import com.study.japanese.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;
import static com.study.japanese.constraint.Constants.User.NOT_EQUAL_PASS;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/join/joinForm")
    String getJoinForm() {
        return "user/join";
    }


    @GetMapping("/auth/info/postList")
    String getUserWritingPosts(@AuthenticationPrincipal PrincipalDetail curUser, Model model) {
        List<PostDto.WritingResponse> resPostsDto = postService.getPostsByUserId(curUser.getUsername());
        model.addAttribute("posts", resPostsDto);
        return "user/postList";
    }

    @GetMapping("/auth/info/commentList")
    String getUserCommentedPosts(@AuthenticationPrincipal PrincipalDetail curUser, Model model) {
        Set<PostDto.UserCommentedPostRow> resUserCommentdPostDtos =
                postService.getPostsByUserCommented(curUser.getUsername());
        model.addAttribute("posts", resUserCommentdPostDtos);
        return "user/commentList";
    }

    @GetMapping("/auth/info/user/delete")
    String getUserDeleteForm(
            UserDto.UserDeleteRequest request,
            @AuthenticationPrincipal PrincipalDetail curUser, Model model) {

        return "user/deleteUser";
    }
    @PostMapping("/auth/info/user/delete")
    String deleteUser(
            @Validated UserDto.UserDeleteRequest request, BindingResult bindingResult,
            @AuthenticationPrincipal PrincipalDetail curUser, Model model) {
        logger.info("USERPASS:"+request.getUserPass());
        String encodePass = encoder.encode(request.getUserPass());
        logger.info("encodePass:"+encodePass);
        logger.info("cur:"+curUser.getPassword());
        if(bindingResult.hasErrors())
            return "user/deleteUser";
        if(!passCheck(curUser.getPassword(), request.getUserPass())){
            bindingResult.addError(new FieldError("userDeleteRequest","userPass",NOT_EQUAL_PASS));
            return "user/deleteUser";
        }

        userService.deleteUser(curUser.getUsername());

        return "redirect:/main";
    }

    @GetMapping("/auth/info/myInfo")
    String getInfoView(Model model, @AuthenticationPrincipal PrincipalDetail curUser) {
        UserDto.InfoResponse infoResponse = userService.getUserInfo(curUser.getUsername());
        model.addAttribute("user", infoResponse);
        return "user/info";
    }

    @GetMapping("/find/passForm")
    String getFindIdForm(Model model) {

        return "user/findPass";
    }

    @GetMapping("/find/idForm")
    String getFindPassForm() {

        return "user/findId";
    }

    @GetMapping("/find/id/{sessionId}")
    String getUserFoundId(
            Model model,
            HttpServletRequest request,
            @PathVariable String sessionId) {
        if (request.getSession().getId().equals(sessionId)) {
            HttpSession session = request.getSession();
            model.addAttribute("userId", session.getAttribute("userFoundId"));
            return "user/FoundUserId";
        } else
            return "errors/authError";


    }


    @GetMapping("/find/pass/{sessionId}")
    String getUserFoundPass(
            Model model,
            HttpServletRequest request,
            @PathVariable String sessionId) {
        if (request.getSession().getId().equals(sessionId)) {
            return "user/FoundUserPass";
        } else
            return "errors/authError";


    }

    boolean passCheck(String curPass, String requestPass){
        return encoder.matches(requestPass,curPass);
    }
}