package com.gdutelc.snp.producer;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author kid
 */
@Component
public class PhoneProducer {
    @Resource
    private RedisTemplate<String,Object> redis;

    public void publish(Object msg){
        redis.convertAndSend("phone",msg);
    }

}
