package com.study.japanese.dto;

import com.study.japanese.entity.Category;
//import com.study.japanese.entity.CategoryDto;
import com.study.japanese.entity.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
public class BoardDto {
    private int id;

    private Category category;

    private String boardName;

    private List<PostDto> posts;
}
