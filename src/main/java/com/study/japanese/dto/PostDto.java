package com.study.japanese.dto;

import com.study.japanese.entity.Board;
import com.study.japanese.entity.Comment;
import com.study.japanese.entity.Post;
import com.study.japanese.entity.User;
import com.study.japanese.function.DateSetting;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
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
    @ToString
    public static class WritingRequest{
        private User user;

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


    @ToString
    @Getter
    @Setter
    public static class ViewCountByDate implements Comparable<PostDto.ViewCountByDate>{
        private String view_;
        private Date date_;

        public String getDate_() {
            return DateSetting.setDateFormat(this.date_);
        }
        @Override
            public int compareTo(PostDto.ViewCountByDate o) {
                return this.date_.compareTo(o.date_);
        }

    }




}
