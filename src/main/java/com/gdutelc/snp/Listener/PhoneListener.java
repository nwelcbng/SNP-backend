package com.gdutelc.snp.Listener;

import com.gdutelc.snp.config.message.MsgConfig;
import com.gdutelc.snp.exception.UserServiceException;
import com.gdutelc.snp.result.Status;
import com.gdutelc.snp.util.CodeUtil;
import com.gdutelc.snp.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;

@Component
public class PhoneListener implements MessageListener{
    private static final Logger log = LoggerFactory.getLogger(PhoneListener.class);

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private CodeUtil codeUtil;

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        Lock re=redisLockRegistry.obtain("lock");
        try {
            re.lock();
            log.info("从消息通道={}监听到消息",new String(pattern));
            log.info("电话元消息={}",new String(message.getBody()));
            String phone = new String(message.getBody());
            String newPhone =  phone.replace("\"", "");
            String code = codeUtil.newCode();
            redisUtil.set(newPhone, code, 130);
            MsgConfig.sendPhoneMessage(phone,code);
            log.info("发送验证码为={}",code);
            log.info("发送验证码成功");
        }catch (Exception e){
            throw new UserServiceException(Status.PHONECODEERROR,e.getMessage());
        }finally {
            re.unlock();
        }
    }
}
