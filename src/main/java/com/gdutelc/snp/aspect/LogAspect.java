package com.gdutelc.snp.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

/**
 * @author kid
 */
@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Pointcut("execution(public  * com.gdutelc.snp.controller.UserApiController.*(..))")
    public void userLog(){}

    @Pointcut("execution(public * com.gdutelc.snp.controller.AdminApiController.*(..))")
    public void adminLog(){}


    @Before("userLog() || adminLog()")  //在切入点方法run之前
    public void logBeforeController(JoinPoint joinPoint){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        logger.info(">>>>>>>>>>Ip: "+request.getRemoteAddr()+"访问了 "+ request.getRequestURL().toString()+ "调用了"+joinPoint.getSignature().getName()+"方法");

    }

}
