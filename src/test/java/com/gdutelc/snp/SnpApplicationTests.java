package com.gdutelc.snp;
import com.gdutelc.snp.config.jwt.UserWebJwtConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class SnpApplicationTests {
    @Resource
    private UserWebJwtConfig userWebJwtConfig;




    @Test
    void contextLoads() {
        Map<String,Object> claims = new HashMap<>();
        claims.put("uid",2);
        claims.put("phone",true);
        String data = userWebJwtConfig.createJwt(claims, "oPndU5VebQBZu9V_z0eTSdI1xAkQ");
        System.out.println(data);


    }

}
