package com.gdutelc.snp.util;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author kid
 */

@Component
@SuppressWarnings("unchecked")
public class PhoneUtil {

    @Resource
    private  KafkaTemplate kafkaTemplate;

    @Resource
    private RedisUtil redisUtil;


    public  void sendMessage(String phone){
        kafkaTemplate.send("phone",phone);
    }

    public boolean checkCode(String code, String phone){
        return redisUtil.get(phone).equals(code);
    }


}
