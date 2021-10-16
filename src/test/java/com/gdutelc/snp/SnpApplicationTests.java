package com.gdutelc.snp;
import com.gdutelc.snp.cache.IsignCache;
import com.gdutelc.snp.cache.UserCache;
import com.gdutelc.snp.config.jwt.AdminJwtConfig;
import com.gdutelc.snp.config.jwt.UserJwtConfig;
import com.gdutelc.snp.dao.IAdminDao;
import com.gdutelc.snp.dao.ISignDao;
import com.gdutelc.snp.dao.IUserDao;
import com.gdutelc.snp.entity.Sign;
import com.gdutelc.snp.service.AdminApiService;
import com.gdutelc.snp.service.UserApiService;
import com.gdutelc.snp.util.JwtUtil;
import com.gdutelc.snp.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
class SnpApplicationTests {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ISignDao signDao;



    @Test
    void contextLoads() {

//        boolean statusTime = redisUtil.set("StatusTime", 1);
//        System.out.println(statusTime);
//        String str = "3219007324";
//        char c = str.charAt(1);
//        if(c == '1'){
//            System.out.println("true");
//        }else{
//            System.out.println("false");
//        }
        List<Sign> allSign = signDao.getAllSign();
        for(Sign sign: allSign){
            char c = sign.getSno().charAt(1);
            if (c == '1'){
                signDao.updateGenderByUid(sign.getUid(),true);
            }else{
                signDao.updateGenderByUid(sign.getUid(),false);
            }
        }

    }

}
