package com.study.japanese.entity;

import com.study.japanese.role.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Table( name = "USER_TABLE")
public class User {
    @Id
    @Column(length = 20)
    private String id;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @OneToMany( mappedBy = "user", fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany( mappedBy = "user", fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany( mappedBy = "user", fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private List<Recommend> recommends;

    @CreationTimestamp
    Timestamp createdDate;

    @Column(length = 20, columnDefinition = "varchar2(20) default 'USER'")
    @Enumerated(value = EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Column(length = 1)
    @ColumnDefault("0")
    private int banCheck;

    public String updateUserName(String name){
        this.name = name;
        return this.name;
    }
    public String updateUserEmail(String email){
        this.email = email;
        return this.email;
    }

}
