package com.gdutelc.snp.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import reactor.util.annotation.NonNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 基本jwt配置
 * @author kid
 */

public class BaseJwtConfig {
    /**
     * 加密的盐
     */
    protected String salt;
    /**
     * 过期时间
     */
    protected long expire;
    /**
     * 头部
     */
    protected String header;



    public String createJwt(Map<String,Object> claims, String openid){
        //现在的时间
        Date date = new Date();
        //到期的时间
        Date expireDate =new Date(date.getTime() + expire*1000);
        //加密的新盐
        Algorithm algorithm = Algorithm.HMAC256(salt+openid);
        //创建jwt
        JWTCreator.Builder jwt = JWT.create();
        //增加发送时间和到期时间
        jwt.withIssuedAt(date).withExpiresAt(expireDate);
        //增加payload
        claims.forEach((k,v)->jwt.withClaim(k,v.toString()));
        //签名
        String sign = jwt.sign(algorithm);
        return sign;

    }
    /**
     * 检查jwt是否失效和合法
     * @param jwt jwt字符串
     * @param openid openid 目前为空
     * @return true || false
     */
    public boolean checkJwt(@NonNull String jwt, String openid){
        Algorithm algorithm = Algorithm.HMAC256(salt+openid);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try{
            verifier.verify(jwt);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Map<String,String> getPayload(@NonNull String jwt){
        Map<String, Claim> map = JWT.decode(jwt).getClaims();
        Map<String,String> payload = new HashMap<>(4);
        map.forEach((k,v)->payload.put(k,v.asString()));
        return payload;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
