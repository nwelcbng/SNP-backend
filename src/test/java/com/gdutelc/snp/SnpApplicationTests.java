package com.gdutelc.snp;

import com.gdutelc.snp.annotation.UserWebJwt;
import com.gdutelc.snp.config.jwt.UserWebJwtConfig;
import com.gdutelc.snp.dao.ISignDao;
import com.gdutelc.snp.dao.IUserDao;
import com.gdutelc.snp.entity.Sign;
import com.gdutelc.snp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SnpApplicationTests {
    @Resource
    private UserWebJwtConfig userWebJwtConfig;

    @Resource
    private ISignDao iSignDao;



    @Test
    void contextLoads() {
//        Map<String,Object> claims = new HashMap<>();
//        claims.put("uid",1);
//        claims.put("phone",true);
//        String data = userWebJwtConfig.createJwt(claims, "oPndU5eWqEkM2101oOOf_rWSM3Fk");
//        System.out.println(data);
//        boolean judge = userWebJwtConfig.checkJwt(data, "oPndU5eWqEkM2101oOOf_rWSM3Fk");

        Sign dsignByUid = iSignDao.getDsignByUid(1);
        System.out.println(dsignByUid);

    }

}
