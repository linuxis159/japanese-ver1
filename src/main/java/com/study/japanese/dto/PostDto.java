package com.study.japanese.dto;

import com.study.japanese.entity.Board;
import com.study.japanese.entity.Comment;
import com.study.japanese.entity.User;
import com.study.japanese.function.DateSetting;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class PostDto {
    private int id;

    private User user;

    private String userId;

    private Board board;

    private int boardId;

    private String title;

    private String content;

    private List<Comment> comments;

    private Timestamp createdDate;

    private int view;

    private int recommendCount;

    @Getter
    @Setter
    public static class WritingRequest{
        private int id;

        private User user;

        private String userId;

        private Board board;

        private int boardId;

        @NotBlank( message = "제목을 입력해주세요")
        private String title;

        @NotBlank( message = "내용을 입력해주세요")
        private String content;
    }

    @Getter
    @Setter
    public static class WritingResponse{
        private int id;

        private UserDto user;

        private String title;

        private String content;

        private List<CommentDto.Response> comments;

        private Timestamp createdDate;

        private int recommendCount;

        private int view;

        public String getCreatedDate() {

            return DateSetting.setDateFormat(this.createdDate);
        }

    }

    @Getter
    @Setter
    public static class UpdateRequest{
        private int id;

        private String title;

        private String content;

    }

    @Getter
    @Setter
    public static class PostListRow{
        private int id;
        private UserDto user;
        private String title;
        private Timestamp createdDate;
        private int view;
        private int recommendCount;

        public String getCreatedDate() {
            return DateSetting.setDateFormat(this.createdDate);
        }
    }

    @Getter
    @Setter
    public static class UserCommentedPostRow{
        private int id;
        private UserDto user;
        private String title;
        private Timestamp createdDate;
        private int view;
        private int recommendCount;

        public String getCreatedDate() {
            return DateSetting.setDateFormat(this.createdDate);
        }

        @Override
        public int hashCode(){
            return Objects.hash(id);
        }
        @Override
        public boolean equals(Object o){
            PostDto.UserCommentedPostRow target = (PostDto.UserCommentedPostRow)o;
            return target.id == this.id;

        }

    }


    @Getter
    @Setter
    public static class PagingPosts{
        private Page<PostDto.PostListRow> posts;
        private int boardId;
        private String sort;
        private String order;
    }




}
