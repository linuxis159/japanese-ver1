package com.study.japanese.interceptor;

import com.study.japanese.repository.BoardRepository;
import com.study.japanese.admin.repository.CategoryRepository;
import com.study.japanese.entity.Board;
import com.study.japanese.entity.Category;
import com.study.japanese.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class NavbarInterceptor implements HandlerInterceptor {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BoardRepository boardRepository;



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView){
        List<Board> boards = new ArrayList();
        List<Category> categories = new ArrayList();
        try {
            boards = boardRepository.findAll();
            categories = categoryRepository.findAll();
            assert modelAndView != null;
            modelAndView.addObject("boards", boards);
            modelAndView.addObject("categories", categories);
        }
        catch (Exception e){
        }
    }
}
