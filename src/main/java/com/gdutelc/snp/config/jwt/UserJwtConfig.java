package com.gdutelc.snp.config.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author kid
 */
@Component
@ConfigurationProperties(prefix = "config.jwt.user")
public class UserJwtConfig extends BaseJwtConfig{

    @Override
    public String createJwt(Map<String, Object> claims, String openid) {
        return super.createJwt(claims, openid);
    }
}
