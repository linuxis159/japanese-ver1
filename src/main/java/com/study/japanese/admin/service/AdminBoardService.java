package com.study.japanese.admin.service;

import com.study.japanese.dto.BoardDto;
import com.study.japanese.entity.Category;
import com.study.japanese.exception.EntityNotFoundExcepiton;
import com.study.japanese.repository.BoardRepository;
import com.study.japanese.admin.repository.CategoryRepository;
import com.study.japanese.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.study.japanese.constraint.Constants.Exception.CATEGORY_NOT_FOUND_EXCEPTION_MESSAGE;

@Service
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminBoardService {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper;

    public int addBoard(BoardDto dto){
        try {
            Category category = categoryRepository.findById(dto.getId())
                    .orElseThrow( () -> new EntityNotFoundExcepiton(CATEGORY_NOT_FOUND_EXCEPTION_MESSAGE));
            dto.setCategory(category);
            Board board = modelMapper.map(dto,Board.class);
            boardRepository.save(board);
            return 1;
        }
        catch (Exception e){
            System.out.println("addBoard ERROR");
            return 0;

        }

    }

}
