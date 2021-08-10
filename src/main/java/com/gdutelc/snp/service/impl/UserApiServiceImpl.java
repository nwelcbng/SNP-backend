package com.gdutelc.snp.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gdutelc.snp.config.jwt.UserJwtConfig;
import com.gdutelc.snp.config.jwt.UserWebJwtConfig;
import com.gdutelc.snp.dao.ISignDao;
import com.gdutelc.snp.dao.IUserDao;
import com.gdutelc.snp.entity.Sign;
import com.gdutelc.snp.exception.GetFormErrorException;
import com.gdutelc.snp.exception.QrCodeErrorException;
import com.gdutelc.snp.exception.RegisterErrorException;
import com.gdutelc.snp.service.UserApiService;
import com.gdutelc.snp.util.FormUtil;
import com.gdutelc.snp.util.QrCodeUtil;
import com.gdutelc.snp.util.RedisUtil;
import com.github.kevinsawicki.http.HttpRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kid
 */

@Service
public class UserApiServiceImpl implements UserApiService {
    @Value("${config.register.appid}")
    private String appid;
    @Value("${config.register.secret}")
    private String secret;

    @Autowired
    private UserJwtConfig userJwtConfig;



    @Autowired
    private IUserDao userDao;

    @Autowired
    private ISignDao iSignDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private QrCodeUtil qrCodeUtil;

    @Autowired
    private UserWebJwtConfig userWebJwtConfig;



    @Override
    public String registerService(String code) {
        //解析json中的code
        JSONObject jsonObject = JSON.parseObject(code);
        String newcode = jsonObject.getString("code");
        System.out.println(newcode);
        //存入地址格式
        Map<String,String> data = new HashMap<>(8);
        data.put("appid", this.appid);
        data.put("secret", this.secret);
        data.put("js_code", newcode);
        data.put("grant_type", "authorization_code");
        String response = HttpRequest.get("https://api.weixin.qq.com/sns/jscode2session").form(data).body();
        if (response.isEmpty()){

            throw new RegisterErrorException("返回内容为空");
        }
        JSONObject object = JSON.parseObject(response);
        //获取openid
        String openid = object.getString("openid");
        String session = object.getString("session_key");
        System.out.println("response: "+response);
        System.out.println("openid: "+openid);
        if (openid==null||session== null){
            return response;
        }
        //将openid和session_key存入redis
        if(!redisUtil.hasKey(session)){
            redisUtil.set(openid,session,0);

        }else{
            throw new RegisterErrorException("该用户的session已过期");
        }

        //信息存入数据库
        Integer judge = userDao.insertOidPhone(openid, "0");
        if (!judge.equals(1)){
            throw new RegisterErrorException("无法往数据库存入user信息");
        }

        Integer uid = userDao.getUserByOpenid(openid).getUid();
        Map<String,Object> claims = new HashMap<>(4);
        claims.put("uid",uid);
        return userJwtConfig.createJwt(claims, openid);

    }

    @Override
    public boolean getPhoneService(String jwt, String request){
        //获取需要的openid session_key
        JSONObject jsonObject = JSON.parseObject(request);
        String iv = jsonObject.getString("iv");

        String encryptedData = jsonObject.getString("encryptedData");
        Map<String, String> payload = userJwtConfig.getPayload(jwt);
        String uid = payload.get("uid");

        String openid = userDao.getOpenidByUid(Integer.parseInt(uid));
        String session = redisUtil.get(openid);
        //解密获取电话号码
        byte[] data = Base64.decodeBase64(encryptedData);
        byte[] key = Base64.decodeBase64(session);
        byte[] vector = Base64.decodeBase64(iv);
        try {
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(vector);
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            instance.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            String info = new String(instance.doFinal(data), StandardCharsets.UTF_8);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
        }
        //TODO 未知获取手机号是否成功hhh
        return false;
    }


    @Override
    public String getFormService(String jwt) {
        //从数据库查找用户信息
        String uid = userJwtConfig.getPayload(jwt).get("uid");
        Sign userinfo = iSignDao.getSignByUid(Integer.parseInt(uid));
        if (userinfo == null){
            throw new GetFormErrorException("获取表单信息失败");
        }
        return FormUtil.tranMapFromSign(userinfo);
    }

    @Override
    public String getWebFormService(String jwt) {
        String uid = userWebJwtConfig.getPayload(jwt).get("uid");
        Sign userinfo = iSignDao.getSignByUid(Integer.parseInt(uid));
        if (userinfo == null){
            throw new GetFormErrorException("获取表单信息失败");
        }
        return FormUtil.tranMapFromSign(userinfo);

    }

    @Override
    public boolean setStatusService(String jwt, String request) {
        //解析json获取参数
        JSONObject jsonObject = JSON.parseObject(request);
        Integer check = jsonObject.getInteger("check");
        String reason = jsonObject.getString("reason");

        String uid = userJwtConfig.getPayload(jwt).get("uid");
       String openid = userDao.getUserByUid(Integer.parseInt(uid)).getOpenid();
        Integer integer = userDao.updateCheckQueByOid(check, reason, openid);
        return openid != null && integer.equals(1);
    }

    @Override
    public String getQrcodeService() {
        return qrCodeUtil.gengerateCode();

    }

    @Override
    public boolean loginByCodeService(String jwt,String uuid) {
        try{
            String uid = userJwtConfig.getPayload(jwt).get("uid");
            String openid = userDao.getOpenidByUid(Integer.parseInt(uid));
            Map<String,Object> claims = new HashMap<>(4);
            claims.put("uid",uid);
            String newjwt = userWebJwtConfig.createJwt(claims, openid);
            String newdata = qrCodeUtil.checkCode(uuid, jwt);
            redisUtil.set(newdata,newjwt,180);
            return true;
        }catch(Exception e){
            throw new QrCodeErrorException("二维码检验失败");
        }

    }


}
