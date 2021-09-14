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
            Qrcode qrcode = new Qrcode(0,0);
            String encrypt = aesUtil.encrypt(uuid);
            redisUtil.set(uuid,qrcode,180);
            return encrypt;
        }catch (Exception e){
            throw new UserServiceException(Status.GETQRCODEERROR);
        }
    }
    public boolean checkCode(String uuid,String uid){
        //解密uuid
        String decrypt = aesUtil.decrypt(uuid);
        boolean judge = redisUtil.hasKey(decrypt);
        if(judge){
            Qrcode qrcode = new Qrcode(Integer.parseInt(uid),1);
            redisUtil.set(decrypt,qrcode,180);
            return true;
        }else{
            throw new UserServiceException(Status.CHECKQRCODEERROR);
        }
    }
}
