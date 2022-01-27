package com.study.japanese.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    Category category;

    @Column(nullable = false, length = 20)
    private String name;

    @OneToMany
    @JoinColumn(name = "boardId")
    private List<Post> posts;






}