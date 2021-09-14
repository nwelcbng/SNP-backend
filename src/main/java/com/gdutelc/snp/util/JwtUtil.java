package com.gdutelc.snp.util;

import com.gdutelc.snp.config.jwt.AdminJwtConfig;
import com.gdutelc.snp.config.jwt.UserJwtConfig;
import com.gdutelc.snp.config.jwt.UserWebJwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kid
 */
@Component
public class JwtUtil {

    private UserJwtConfig userJwtConfig;

    private UserWebJwtConfig userWebJwtConfig;

    private AdminJwtConfig adminJwtConfig;

    @Autowired
    public void setUserJwtConfig(UserJwtConfig userJwtConfig) {
        this.userJwtConfig = userJwtConfig;
    }
    @Autowired
    public void setUserWebJwtConfig(UserWebJwtConfig userWebJwtConfig) {
        this.userWebJwtConfig = userWebJwtConfig;
    }
    @Autowired
    public void setAdminJwtConfig(AdminJwtConfig adminJwtConfig) {
        this.adminJwtConfig = adminJwtConfig;
    }

    public String userJwtCreate(Integer uid, boolean phone,String openid){
        Map<String, Object> claims = new HashMap<>(4);
        claims.put("uid",uid);
        claims.put("phone",phone);
        return userJwtConfig.createJwt(claims, openid);
    }
    public String userWebJwtCreate(Integer uid, boolean phone,String openid){
        Map<String, Object> claims = new HashMap<>(4);
        claims.put("uid",uid);
        claims.put("phone",phone);
        return userWebJwtConfig.createJwt(claims, openid);
    }
    public String adminJwtCreate(Integer aid, String username,String password){
        Map<String, Object> claims = new HashMap<>(4);
        claims.put("aid",aid);
        claims.put("username",username);
        return adminJwtConfig.createJwt(claims, password);
    }

}
