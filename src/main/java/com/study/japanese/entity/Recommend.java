package com.study.japanese.entity;

import com.study.japanese.idclass.RecommendKey;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;




@Getter
@Setter
@Entity
@IdClass(RecommendKey.class)
public class Recommend {


    @Id
    @ManyToOne
    @JoinColumn( name="userId")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn( name="postId")
    private Post post;

    @Column
    @ColumnDefault("1")
    private long recommendCheck;


}
