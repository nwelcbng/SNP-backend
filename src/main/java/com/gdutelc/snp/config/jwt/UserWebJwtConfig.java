package com.gdutelc.snp.config.jwt;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author kid
 */
@Component
@ConfigurationProperties(prefix = "config.jwt.userweb")
public class UserWebJwtConfig extends BaseJwtConfig{
}
