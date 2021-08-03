package com.gdutelc.snp.interceptor;

import com.gdutelc.snp.annotation.UserJwt;
import com.gdutelc.snp.config.jwt.UserJwtConfig;
import com.gdutelc.snp.dao.IAdminDao;
import com.gdutelc.snp.dao.IUserDao;
import com.gdutelc.snp.exception.JwtErrorException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author 10501
 */
@Component
public class AdminJwtInterceptor implements HandlerInterceptor {
    private UserJwtConfig userJwtConfig;
    private IAdminDao iAdminDao;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            //如果handler不是方法则放行
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        boolean isAnnotate = method.isAnnotationPresent(UserJwt.class);
        //如果方法上没有注解则放行
        if(!isAnnotate){
            return true;
        }
        UserJwt annotation = method.getAnnotation(UserJwt.class);
        //如果注解中的require修改为false则放行
        if(!annotation.require()){
            return true;
        }
        String cookie = request.getHeader("Cookie");
        if(cookie == null || cookie.isBlank()){
            throw new JwtErrorException("jwt为空");
        }
        String aid = userJwtConfig.getPayload(cookie).get("aid");
        String password = iAdminDao.getPassWordByAid(Integer.parseInt(aid));
        boolean judge = userJwtConfig.checkJwt(cookie, password);
        if(!judge){
            throw new JwtErrorException("管理员jwt不为空但openid已修改或者jwt超过过期时间");
        }
        return true;
    }
}
