package com.gdutelc.snp.util;
import com.gdutelc.snp.exception.QrCodeErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.UUID;

/**
 * @author kid
 */
@Component
public class QrCodeUtil {

    @Autowired
    private  RedisUtil redisUtil;

    @Autowired
    private AesUtil aesUtil;
    public String gengerateCode(){
        try{
            String uuid = UUID.randomUUID().toString();
            String encrypt = aesUtil.encrypt(uuid);
            String code = Integer.toString(0);
            redisUtil.set(uuid,code,180);
            return encrypt;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public String checkCode(String encrypt,String jwt){
        String uuid = aesUtil.decrypt(encrypt);
        boolean judge = redisUtil.hasKey(uuid);
        if(judge){
            redisUtil.set(uuid,"1",180);
            String newdata = "user" + uuid;
            redisUtil.set(newdata,jwt,180);
            return newdata;
        }else{
            throw new QrCodeErrorException("二维码确认失败");
        }

    }
}
