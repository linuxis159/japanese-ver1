package com.study.japanese.repository;


import com.study.japanese.embeddedid.RecommendKey;
import com.study.japanese.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, RecommendKey> {
    long countByPostId(int postId);
}


