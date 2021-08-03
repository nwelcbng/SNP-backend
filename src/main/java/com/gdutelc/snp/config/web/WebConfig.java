package com.gdutelc.snp.config.web;

import com.gdutelc.snp.interceptor.UserJwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 10501
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private UserJwtInterceptor userJwtInterceptor;


    @Autowired
    public void setUserJwtInterceptor(UserJwtInterceptor userJwtInterceptor){
        this.userJwtInterceptor = userJwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userJwtInterceptor).addPathPatterns("/**");
    }
}
