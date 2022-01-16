package com.study.japanese.dto;

import com.study.japanese.dto.BoardDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
public class CategoryDto {
    private int id;

    private String name;

    private List<BoardDto> boards;
}
