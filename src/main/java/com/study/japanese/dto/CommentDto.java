package com.study.japanese.dto;


import com.study.japanese.entity.Comment;
import com.study.japanese.entity.Post;
import com.study.japanese.entity.User;
import com.study.japanese.function.DateSetting;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Setter
@Getter
@ToString
public class CommentDto{
    private int Id;
    private int postId;
    private PostDto post;
    private UserDto user;
    private String content;
    private Timestamp createdDate;

    public String getCreatedDate(){
        return DateSetting.setDateFormat(this.createdDate);
    }



    @Getter
    @Setter
    public static class WritingRequest{
        private Post post;

        private User user;

        private int postId;

        @NotBlank
        @Size(max = 250)
        private String content;
    }

    @Getter
    @Setter
    public static class Response implements Comparable<CommentDto.Response>{
        private int Id;
        private UserDto user;
        private String content;
        private Timestamp createdDate;
        public String getCreatedDate(){
            return DateSetting.setDateFormat(this.createdDate);
        }

        @Override
        public int compareTo(CommentDto.Response o) {
            return this.createdDate.compareTo(o.createdDate);
        }

    }





}
