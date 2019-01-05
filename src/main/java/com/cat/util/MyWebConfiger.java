package com.cat.util;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyWebConfiger implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //不拦截
        List<String> list = new ArrayList<>();
        list.add("/userLogin");
        list.add("/regist");
        list.add("/ajaxName");
        list.add("/login.html");
        list.add("/regist.html");
        list.add("/images/**");
        list.add("/css/**");
        list.add("/js/**");
        registry.addInterceptor(new MyInterceptor())
                .addPathPatterns("/**").excludePathPatterns(list);
    }
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }
}
