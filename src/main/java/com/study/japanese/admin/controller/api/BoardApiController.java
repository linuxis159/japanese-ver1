package com.study.japanese.admin.controller.api;

import com.study.japanese.admin.dto.AddBoardDto;
import com.study.japanese.admin.service.AdminBoardService;
import com.study.japanese.dto.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class BoardApiController {
    @Autowired
    AdminBoardService boardService;
    @PostMapping("/board/add")
    public int addBoard(@RequestBody BoardDto dto){
        int result = boardService.addBoard(dto);
        return result;

    }

}
