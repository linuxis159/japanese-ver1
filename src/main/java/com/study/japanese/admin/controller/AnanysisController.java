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

    @GetMapping("")
    String analysis(){
        return "admin/analysis/analysis";
    }

    @GetMapping("/view")
    String analysisView(Model model){
        List<AnalysisDto.ViewCountByDate> viewCountByDate = new ArrayList();

        List<Object[]> viewAnalysisByDate = postRepository.viewCountByDate();
        for(int i=0; i<viewAnalysisByDate.size(); i++){
            AnalysisDto.ViewCountByDate data = new AnalysisDto.ViewCountByDate();
            data.setDate_(Date.valueOf(viewAnalysisByDate.get(i)[0].toString()));
            data.setView_(viewAnalysisByDate.get(i)[1].toString());
            viewCountByDate.add(data);
        }

        logger.info("viewCountByDate"+viewCountByDate);
        Collections.sort(viewCountByDate);
        logger.info("viewCountByDate"+viewCountByDate);
        model.addAttribute("viewCountByDate",viewCountByDate);

        return "admin/analysis/analysisView";
    }

    @GetMapping("/post")
    String analysisPost(Model model){
        List<AnalysisDto.PostCountByDate> postCountByDate = new ArrayList();

        List<Object[]> postAnalysisByDate = postRepository.postCountByDate();
        for(int i=0; i<postAnalysisByDate.size(); i++){
            AnalysisDto.PostCountByDate data = new AnalysisDto.PostCountByDate();
            data.setDate_(Date.valueOf(postAnalysisByDate.get(i)[0].toString()));
            data.setPostCount_(postAnalysisByDate.get(i)[1].toString());
            postCountByDate.add(data);
        }
        logger.info("viewCountByDate"+postCountByDate);
        Collections.sort(postCountByDate);
        logger.info("viewCountByDate"+postCountByDate);
        model.addAttribute("postCountByDate",postCountByDate);

        return "admin/analysis/analysisPost";
    }

    @GetMapping("/comment")
    String analysisComment(Model model){
        List<AnalysisDto.CommentCountByDate> CommentCountByDate = new ArrayList();

        List<Object[]> commentAnalysisByDate = commentRepository.commentCountByDate();
        for(int i=0; i<commentAnalysisByDate.size(); i++){
            AnalysisDto.CommentCountByDate data = new AnalysisDto.CommentCountByDate();
            data.setDate_(Date.valueOf(commentAnalysisByDate.get(i)[0].toString()));
            data.setCommentCount_(commentAnalysisByDate.get(i)[1].toString());
            CommentCountByDate.add(data);
        }
        logger.info("viewCountByDate"+CommentCountByDate);
        Collections.sort(CommentCountByDate);
        logger.info("viewCountByDate"+CommentCountByDate);
        model.addAttribute("commentCountByDate",CommentCountByDate);

        return "admin/analysis/analysisComment";
    }

}
