package com.gdutelc.snp.config.web;

import com.gdutelc.snp.interceptor.AdminJwtInterceptor;
import com.gdutelc.snp.interceptor.UserJwtInterceptor;
import com.gdutelc.snp.interceptor.UserWebJwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 10501
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private UserJwtInterceptor userJwtInterceptor;

    private UserWebJwtInterceptor userWebJwtInterceptor;

    private AdminJwtInterceptor adminJwtInterceptor;


    @Autowired
    public void setUserJwtInterceptor(UserJwtInterceptor userJwtInterceptor){
        this.userJwtInterceptor = userJwtInterceptor;
    }
    @Autowired
    public void setUserWebJwtInterceptor(UserWebJwtInterceptor userWebJwtInterceptor) {
        this.userWebJwtInterceptor = userWebJwtInterceptor;
    }
    @Autowired
    public void setAdminJwtInterceptor(AdminJwtInterceptor adminJwtInterceptor) {
        this.adminJwtInterceptor = adminJwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userJwtInterceptor).addPathPatterns("/**");
        registry.addInterceptor(adminJwtInterceptor).addPathPatterns("/**");
        registry.addInterceptor(userWebJwtInterceptor).addPathPatterns("/**");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://snp.gdutelc.com/")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
