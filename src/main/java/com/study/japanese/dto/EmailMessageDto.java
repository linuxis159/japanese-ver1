package com.study.japanese.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import static com.study.japanese.constraint.Constants.Post.EMPTY_TITLE;
import static com.study.japanese.constraint.Constants.Post.EMPTY_CONTENT;

@Getter
@Setter
public class EmailMessageDto {
    @NotBlank(message = EMPTY_TITLE)
    String subject;
    @NotBlank(message = EMPTY_CONTENT)
    String message;
}
