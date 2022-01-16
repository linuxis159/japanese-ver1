package com.study.japanese.dto;

import com.study.japanese.entity.Post;
import com.study.japanese.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
public class RecommendDto {
    private User user;

    private Post post;

    private int recommendCheck;

    @Getter
    @Setter
    public static class Request{
       private User user;
       private Post post;
    }

    @Getter
    @Setter
    public static class Response{
        private int resultState;
        private long recommendCount;

    }
}
