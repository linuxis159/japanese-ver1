package com.study.japanese.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import static com.study.japanese.constraint.Constants.Code.CODE_TIME_LIMIT;


@Getter
@Setter
@AllArgsConstructor
@RedisHash(value = "code", timeToLive = CODE_TIME_LIMIT)
public class Code {
    @Id
    String code;

    int result;
}
