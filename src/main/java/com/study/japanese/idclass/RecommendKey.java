package com.study.japanese.idclass;


import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RecommendKey implements Serializable {
    private String user;
    private int post;

}
