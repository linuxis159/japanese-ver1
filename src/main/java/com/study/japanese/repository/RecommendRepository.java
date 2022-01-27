package com.study.japanese.repository;



import com.study.japanese.entity.Recommend;
import com.study.japanese.idclass.RecommendKey;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RecommendRepository extends JpaRepository<Recommend, RecommendKey> {
    long countByPostId(int postId);
}


