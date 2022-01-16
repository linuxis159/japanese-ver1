package com.study.japanese.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Board> boardList;
}
