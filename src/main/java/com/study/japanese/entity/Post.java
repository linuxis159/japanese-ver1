package com.study.japanese.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;



import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@ToString
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn( name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn( name = "boardId")
    private Board board;


    @Column(nullable = false, length = 20)
    private String title;

    @Lob
    @Column(nullable = false, name="postContent")
    private String content;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Recommend> recommends;

    @CreationTimestamp()
    private Timestamp createdDate;

    @ColumnDefault("0")
    @Column( name = "postView")
    private int view;

    @ColumnDefault("0")
    @Column
    private int recommendCount;
    public int viewCountUp(){
        return ++this.view;
    }

    public void updateTitle(String title){

        this.title = title;
    }
    public void updaContent(String content){
        this.content = content;
    }



}
