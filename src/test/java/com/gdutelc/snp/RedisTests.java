package com.gdutelc.snp;


import com.gdutelc.snp.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTests {
    @Autowired
    private RedisUtil redisUtil;
    @Test
    void RedisTest(){
        boolean judge = redisUtil.set("kid", "1");
        System.out.println(judge);

    }
}
