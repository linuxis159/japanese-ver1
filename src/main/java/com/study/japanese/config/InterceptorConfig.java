package com.study.japanese.config;

import com.study.japanese.interceptor.CountAnalysisInterceptor;
import com.study.japanese.interceptor.NavbarInterceptor;
import com.study.japanese.interceptor.PostManageInterceptor;
import com.study.japanese.interceptor.UserManageInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final NavbarInterceptor navbarInterceptor;

    private final CountAnalysisInterceptor countAnalysisInterceptor;

    private final UserManageInterceptor userManageInterceptor;

    private final PostManageInterceptor postManageInterceptor;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(navbarInterceptor).addPathPatterns("/**");
        registry.addInterceptor(countAnalysisInterceptor).addPathPatterns("/admin/analysis/**");
        registry.addInterceptor(userManageInterceptor).addPathPatterns("/admin/user/**");
        registry.addInterceptor(postManageInterceptor).addPathPatterns("/admin/post/**");
    }


}
