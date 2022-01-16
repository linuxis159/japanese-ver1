package com.study.japanese.embeddedid;

import com.study.japanese.entity.Post;
import com.study.japanese.entity.User;
import lombok.*;


import javax.persistence.*;
import java.io.Serializable;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor

public class RecommendKey implements Serializable {
    private static final long serialVersionUID = -2929789292155268166L;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private int postId;
}
