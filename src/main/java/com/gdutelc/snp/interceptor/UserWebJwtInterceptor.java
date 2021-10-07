package com.gdutelc.snp.interceptor;
import com.gdutelc.snp.annotation.UserWebJwt;
import com.gdutelc.snp.config.jwt.UserJwtConfig;
import com.gdutelc.snp.config.jwt.UserWebJwtConfig;
import com.gdutelc.snp.dao.IUserDao;
import com.gdutelc.snp.exception.JwtErrorException;
import com.gdutelc.snp.result.Status;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author kid
 */
@Component
public class UserWebJwtInterceptor implements HandlerInterceptor {
    @Resource
    private UserWebJwtConfig userWebJwtConfig;
    @Resource
    private IUserDao userDao;
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler){
        if(!(handler instanceof HandlerMethod)){
            //如果handler不是方法则放行
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        boolean isAnnotate = method.isAnnotationPresent(UserWebJwt.class);
        //如果方法上没有注解则放行
        if(!isAnnotate){
            return true;
        }
        UserWebJwt annotation = method.getAnnotation(UserWebJwt.class);
        //如果注解中的require修改为false则放行
        if(!annotation.require()){
            return true;
        }
        String cookie = request.getHeader("Authorization");
        if(cookie == null || cookie.isBlank()){
            throw new JwtErrorException(Status.JWTMISSERROR);
        }
        String uid = userWebJwtConfig.getPayload(cookie).get("uid");

        String openid = userDao.getOpenidByUid(Integer.parseInt(uid));
        System.out.println(openid);
        boolean judge = userWebJwtConfig.checkJwt(cookie, openid);
        System.out.println(judge);
        if(!judge){
            throw new JwtErrorException(Status.JWTCHANGE);
        }
        return true;
    }
}
