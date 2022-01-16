package com.study.japanese.repository;

import com.study.japanese.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Optional<List<Comment>> findByUser_Id(String username);

}
