package com.gdutelc.snp;

import com.gdutelc.snp.config.jwt.BaseJwtConfig;
import com.gdutelc.snp.config.jwt.UserJwtConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class JwtTests {
    @Autowired
    private UserJwtConfig jwtConfig;
    @Test
    @DisplayName("测试basejwt")
    void jwtTest(){
        jwtConfig.setExpire(36000);
        jwtConfig.setHeader("jwt");
        HashMap<String,Object> claims = new HashMap<>();
        claims.put("username","kid");
        String jwt = jwtConfig.createJwt(claims, "qwer1234");
        System.out.println(jwt);

    }
    @Test
    @DisplayName("测试basejwt")
    void checkJwtTest(){
        HashMap<String,Object> claims = new HashMap<>();
        claims.put("username","kid");
        String jwt = jwtConfig.createJwt(claims, "qwer1234");
        System.out.println(jwt);
        boolean judge = jwtConfig.checkJwt(jwt, "qwer1234");
        System.out.println(judge);

    }
    @Test
    @DisplayName("测试basejwt")
    void getJwtTest(){
        jwtConfig.setExpire(36000);
        jwtConfig.setHeader("jwt");
        HashMap<String,Object> claims = new HashMap<>();
        claims.put("username","kid");
        String jwt = jwtConfig.createJwt(claims, "qwer1234");
        System.out.println(jwt);
        Map<String, String> payload = jwtConfig.getPayload(jwt);
        payload.forEach((k,v)-> System.out.println(k+"---->"+v));

    }
}
