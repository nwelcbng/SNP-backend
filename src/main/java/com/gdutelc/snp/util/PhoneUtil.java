package com.gdutelc.snp.util;
import com.gdutelc.snp.producer.PhoneProducer;
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
    private PhoneProducer phoneProducer;

    @Resource
    private RedisUtil redisUtil;


    public  void sendMessage(String phone){
        phoneProducer.publish(phone);
    }

    public boolean checkCode(String code, String phone){
        return redisUtil.get(phone).equals(code);
    }


}
