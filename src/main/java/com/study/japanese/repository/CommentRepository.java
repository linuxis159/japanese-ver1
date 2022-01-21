package com.study.japanese.repository;

import com.study.japanese.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Optional<List<Comment>> findByUser_Id(String username);
    @Query(value = "SELECT TO_CHAR(CREATED_DATE,'yyyy-mm-dd') as date_ ,count(*) as commentCount FROM comment_table group by TO_CHAR(created_date,'yyyy-mm-dd')", nativeQuery = true)
    List<Object[]> commentCountByDate();

}
