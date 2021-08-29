package com.gdutelc.snp.util;
import com.gdutelc.snp.entity.Qrcode;
import com.gdutelc.snp.exception.UserServiceException;
import com.gdutelc.snp.result.Status;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author kid
 */
@Component
@SuppressWarnings("unchecked")
public class QrCodeUtil {

    @Resource
    private  RedisUtil redisUtil;

    @Resource
    private AesUtil aesUtil;
    public String gengerateCode(){
        try{
            String uuid = UUID.randomUUID().toString();
            //加密uuid
            String encrypt = aesUtil.encrypt(uuid);
            Qrcode qrcode = new Qrcode(0,0);
            List<String> getuuid = (List<String>) redisUtil.get("uuid");
            if (getuuid != null){
                getuuid.add(uuid);
                redisUtil.set("uuid",getuuid,180);
            }else{
                List<String> newuuid = new LinkedList<>();
                redisUtil.set("uuid",newuuid,180);
            }
            redisUtil.set(uuid,qrcode,180);
            return encrypt;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public boolean checkCode(String encrypt,String uid){
        String uuid = aesUtil.decrypt(encrypt);
        boolean judge = redisUtil.hasKey(uuid);
        if(judge){
            Qrcode qrcode = new Qrcode(Integer.parseInt(uid),1);
            redisUtil.set(uuid,qrcode,180);
            return true;
        }else{
            throw new UserServiceException(Status.CHECKQRCODEERROR);
        }
    }
}
