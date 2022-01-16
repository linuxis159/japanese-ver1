package com.study.japanese.config;

import com.study.japanese.interceptor.NavbarInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    NavbarInterceptor navbarInterceptor;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(navbarInterceptor).addPathPatterns("/**");
    }

}
