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
public class UserManageInterceptor implements HandlerInterceptor {



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView){
        try {
            SidebarDto userList = new SidebarDto();
            SidebarDto mailSend = new SidebarDto();
            List<SidebarDto> sidebar = new ArrayList();

            userList.setLink("/admin/user/list");
            userList.setLabel("유저목록");
            sidebar.add(userList);

            mailSend.setLink("/admin/user/mail/send");
            mailSend.setLabel("유저전체 메일 전송");
            sidebar.add(mailSend);

            modelAndView.addObject("sidebar",sidebar);
            assert modelAndView != null;

        }
        catch (Exception e){
        }
    }
}
