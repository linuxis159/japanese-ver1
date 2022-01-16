package com.study.japanese.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/auth/login").setViewName("login");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/").setViewName("main");
        registry.addViewController("/auth/join").setViewName("join");
    }

}