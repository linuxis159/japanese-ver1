package com.study.japanese.repository;

import com.study.japanese.dto.PostDto;
import com.study.japanese.entity.Post;
import com.study.japanese.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository  extends JpaRepository<Post,Integer> {
    Optional<Page<Post>> findByBoard_Id(@Param("boardID") int boardID, Pageable pageable);
    Optional<List<Post>> findByUser_Id(@Param("userID") String userID);

    @Query(value = "SELECT TO_CHAR(CREATED_DATE,'yyyy-mm-dd') as date_ ,sum(post_view) as view_ FROM post group by TO_CHAR(created_date,'yyyy-mm-dd')", nativeQuery = true)
    List<Object[]> viewCountByDate();

    @Query(value = "SELECT TO_CHAR(CREATED_DATE,'yyyy-mm-dd') as date_ ,count(*) as postCount FROM post group by TO_CHAR(created_date,'yyyy-mm-dd')", nativeQuery = true)
    List<Object[]> postCountByDate();
}
