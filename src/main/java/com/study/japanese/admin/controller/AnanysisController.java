package com.study.japanese.admin.controller;


import com.study.japanese.dto.AnalysisDto;
import com.study.japanese.repository.CommentRepository;
import com.study.japanese.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/analysis")
public class AnanysisController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    Logger logger = LoggerFactory.getLogger(AnanysisController.class);


    @GetMapping(value={"/viewCount","/main"})
    String analysisView(Model model){
        List<AnalysisDto.CountByDate> viewCountByDate = objectToDto(postRepository.viewCountByDate());
        Collections.sort(viewCountByDate);
        model.addAttribute("countByDate",viewCountByDate);
        model.addAttribute("label","최근 14일간 조회 수");
        return "admin/analysis/analysisCount";
    }

    @GetMapping("/postCount")
    String analysisPost(Model model){
        List<AnalysisDto.CountByDate> postCountByDate = objectToDto(postRepository.postCountByDate());
        Collections.sort(postCountByDate);
        model.addAttribute("countByDate",postCountByDate);
        model.addAttribute("label","최근 14일간 글 수");
        return "admin/analysis/analysisCount";
    }

    @GetMapping("/commentCount")
    String analysisComment(Model model){
        List<AnalysisDto.CountByDate> commentCountByDate = objectToDto(commentRepository.commentCountByDate());
        Collections.sort(commentCountByDate);
        model.addAttribute("countByDate",commentCountByDate);
        model.addAttribute("label","최근 14일간 댓글 수");
        return "admin/analysis/analysisCount";
    }

    List<AnalysisDto.CountByDate> objectToDto(List<Object[]> list){
        List<AnalysisDto.CountByDate> countByDate = new ArrayList();
        for(int i=0; i<list.size(); i++){
            AnalysisDto.CountByDate data = new AnalysisDto.CountByDate();
            data.setDate(Date.valueOf(list.get(i)[0].toString()));
            data.setCount(list.get(i)[1].toString());
            countByDate.add(data);
        }
        return countByDate;
    }

}
