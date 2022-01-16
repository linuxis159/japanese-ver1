package com.study.japanese.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.function.ToDoubleFunction;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "COMMENT_TABLE")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn( name="postId")
    private Post post;

    @ManyToOne
    @JoinColumn( name="userId")
    private User user;

    @Column(nullable = false, length = 250)
    private String content;

    @CreationTimestamp
    private Timestamp createdDate;



}

