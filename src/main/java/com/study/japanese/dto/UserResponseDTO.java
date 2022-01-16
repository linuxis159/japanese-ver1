package com.study.japanese.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
public class UserResponseDTO {
    private List<ObjectError> errors;
}
