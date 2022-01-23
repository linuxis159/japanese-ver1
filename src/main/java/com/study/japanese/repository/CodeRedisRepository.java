package com.study.japanese.repository;

import com.study.japanese.entity.Code;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface CodeRedisRepository extends CrudRepository<Code, String> {
}
