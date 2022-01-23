package com.study.japanese.constraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Constants {

    private Constants(){

    }
    public class Post {
        public static final String EMPTY_TITLE = "제목을 입력해주세요";
        public static final String EMPTY_CONTENT = "내용을 입력해주세요";
        public static final String EXCESS_TITLE_LENGTH = "제목은 100자까지입니다";
        public static final String EXCESS_CONTENT_LENGTH = "내용은 1500자까지입니다";

        public static final String YOUTUBE_LINK_PATTERN = "https://www.youtube.com/watch?v=";
        public static final String YOUTUBE_EMBED_PATTERN = "https://www.youtube.com/embed/";
    }
    public class Auth{
        public static final int ALREADY_JOINED = 1;

    }

    public class Exception{
        public static final String POST_NOT_FOUND_EXCEPTION_MESSAGE = "게시물을 찾지 못했습니다";
        public static final String BOARD_NOT_FOUND_EXCEPTION_MESSAGE = "게시판을 찾지 못했습니다";
        public static final String COMMENT_NOT_FOUND_EXCEPTION_MESSAGE = "댓글을 찾지 못했습니다";
        public static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "유저를 찾지 못했습니다";
        public static final String CATEGORY_NOT_FOUND_EXCEPTION_MESSAGE = "카테고리를 찾지 못했습니다";

    }

    public class User{
        public static final String EMPTY_USERID = "아이디를 입력해주세요";
        public static final String EMPTY_PASS = "비밀번호를 입력해주세요";
        public static final String EMPTY_NAME = "닉네임을 입력해주세요";
        public static final String EMPTY_EMAIL = "이메일을 입력해주세요";

        public static final int USERID_MAX = 20;
        public static final int PASS_MAX = 50;
        public static final int NAME_MAX = 20;
        public static final int MEAIL_MAX = 100;

        public static final String NOT_EQUAL_PASS = "비밀번호가 일치하지 않습니다";
    }
    public class Code{
        public static final int CODE_TIME_LIMIT = 300;

    }




}
