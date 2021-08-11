package com.gdutelc.snp;


import com.gdutelc.snp.cache.UserCache;
import com.gdutelc.snp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CacheTests {
    @Autowired
    private UserCache userCache;
    @Test
    public void cacheTests(){
        User user = userCache.getUserByOpenid("oPndU5eWqEkM2101oOOf_rWSM3Fk");
        System.out.println(user.getUid());

    }
}

