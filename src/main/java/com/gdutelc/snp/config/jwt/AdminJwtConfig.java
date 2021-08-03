package com.gdutelc.snp.config.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author kid
 */
@Component
@ConfigurationProperties(prefix = "config.jwt.admin")
public class AdminJwtConfig extends BaseJwtConfig{
    @Override
    public String createJwt(Map<String, Object> claims, String password) {
        return super.createJwt(claims, password);
    }

    @Override
    public boolean checkJwt(String jwt, String password) {
        return super.checkJwt(jwt, password);
    }

}
