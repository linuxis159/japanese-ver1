package com.study.japanese.interceptor;


import com.study.japanese.dto.SidebarDto;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostManageInterceptor implements HandlerInterceptor {



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView){
        try {
            SidebarDto postLinkDto = new SidebarDto();
            List<SidebarDto> sidebar = new ArrayList();
            postLinkDto.setLink("/admin/post/list");
            postLinkDto.setLabel("게시글 목록");
            sidebar.add(postLinkDto);
            modelAndView.addObject("sidebar",sidebar);
            assert modelAndView != null;

        }
        catch (Exception e){
        }
    }
}
