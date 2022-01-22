package com.study.japanese.config;

import com.study.japanese.interceptor.CountAnalysisInterceptor;
import com.study.japanese.interceptor.NavbarInterceptor;
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

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(navbarInterceptor).addPathPatterns("/**");
        registry.addInterceptor(countAnalysisInterceptor).addPathPatterns("/admin/analysis/**");
    }


}
