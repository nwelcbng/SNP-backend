package com.gdutelc.snp.interceptor;
import com.gdutelc.snp.annotation.AdminJwt;
import com.gdutelc.snp.config.jwt.AdminJwtConfig;
import com.gdutelc.snp.dao.IAdminDao;
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
 * @author 10501
 */
@Component
public class AdminJwtInterceptor implements HandlerInterceptor {

    @Resource
    private AdminJwtConfig adminJwtConfig;
    @Resource
    private IAdminDao iAdminDao;
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        if(!(handler instanceof HandlerMethod)){
            //如果handler不是方法则放行
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        boolean isAnnotate = method.isAnnotationPresent(AdminJwt.class);
        //如果方法上没有注解则放行
        if(!isAnnotate){
            return true;
        }
        AdminJwt annotation = method.getAnnotation(AdminJwt.class);
        //如果注解中的require修改为false则放行
        if(!annotation.require()){
            return true;
        }
        String cookie = request.getHeader("Cookie");
        if(cookie == null || cookie.isBlank()){
            throw new JwtErrorException(Status.JWTMISSERROR);
        }
        String aid = adminJwtConfig.getPayload(cookie).get("aid");
        String password = iAdminDao.getPassWordByAid(Integer.parseInt(aid));
        boolean judge = adminJwtConfig.checkJwt(cookie, password);
        if(!judge){
            throw new JwtErrorException(Status.JWTCHANGE);
        }
        return true;
    }
}
