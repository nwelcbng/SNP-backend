package com.gdutelc.snp.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
        Method m = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //通过真实方法获取该方法的参数名称
        LocalVariableTableParameterNameDiscoverer paramNames = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = paramNames.getParameterNames(m);

        Object[] args = joinPoint.getArgs();
        Map<String,Object> map = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], args[i]);
        }
        logger.info(">>>>>>>>>>Ip: "+request.getRemoteAddr()+"访问了 "+ request.getRequestURL().toString()+ "调用了"+joinPoint.getSignature().getName()+"方法");
        logger.info(">>>>>>>传入参数为"+map.toString());
    }
     @AfterReturning(value = "userLog() || adminLog()",returning = "rtv")
     public void logafterController(JoinPoint joinPoint,Object rtv){
        logger.info(">>>>>>>>>>>>>>>返回参数"+rtv.toString());
     }

}
