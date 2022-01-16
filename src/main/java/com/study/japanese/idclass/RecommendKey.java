package com.study.japanese.idclass;

import com.study.japanese.entity.Post;
import com.study.japanese.entity.User;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RecommendKey implements Serializable {
    private String user;
    private int post;

}
