package com.gdutelc.snp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kid
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserJwt {

    /**
     * 标记此jwt是否需要被验证
     * @return 被需要
     */
    boolean require() default true;
}
