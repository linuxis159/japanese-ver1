package com.study.japanese.interceptor;


import com.study.japanese.dto.AnalysisCountSideBarDto;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class CountAnalysisInterceptor implements HandlerInterceptor {



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView){
        try {
            AnalysisCountSideBarDto view = new AnalysisCountSideBarDto();
            AnalysisCountSideBarDto post = new AnalysisCountSideBarDto();
            AnalysisCountSideBarDto comment = new AnalysisCountSideBarDto();
            List<AnalysisCountSideBarDto> sidebar = new ArrayList();
            view.setLink("/admin/analysis/viewCount");
            view.setLabel("조회수");
            sidebar.add(view);
            post.setLink("/admin/analysis/postCount");
            post.setLabel("작성된 게시글 수");
            sidebar.add(post);
            comment.setLink("/admin/analysis/commentCount");
            comment.setLabel("작성된 댓글 수");
            sidebar.add(comment);
            modelAndView.addObject("sidebar",sidebar);
            assert modelAndView != null;

        }
        catch (Exception e){
        }
    }
}
