package com.gdutelc.snp.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gdutelc.snp.cache.SignCache;
import com.gdutelc.snp.cache.UserCache;
import com.gdutelc.snp.config.jwt.UserJwtConfig;
import com.gdutelc.snp.config.jwt.UserWebJwtConfig;
import com.gdutelc.snp.dao.ISignDao;
import com.gdutelc.snp.dao.IUserDao;
import com.gdutelc.snp.dto.Dsign;
import com.gdutelc.snp.entity.Qrcode;
import com.gdutelc.snp.entity.Sign;
import com.gdutelc.snp.entity.User;
import com.gdutelc.snp.exception.*;
import com.gdutelc.snp.result.Status;
import com.gdutelc.snp.service.UserApiService;
import com.gdutelc.snp.util.AesUtil;
import com.gdutelc.snp.util.JwtUtil;
import com.gdutelc.snp.util.QrCodeUtil;
import com.gdutelc.snp.util.RedisUtil;
import com.github.kevinsawicki.http.HttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kid
 */

@Service
@SuppressWarnings("unchecked")
public class UserApiServiceImpl implements UserApiService {
    @Value("${config.register.appid}")
    private String appid;
    @Value("${config.register.secret}")
    private String secret;

    @Resource
    private UserJwtConfig userJwtConfig;

    @Resource
    private IUserDao userDao;

    @Resource
    private ISignDao iSignDao;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private QrCodeUtil qrCodeUtil;

    @Resource
    private UserWebJwtConfig userWebJwtConfig;

    @Resource
    private UserCache userCache;

    @Resource
    private SignCache signCache;


    @Resource
    private KafkaTemplate<String,Object> kafkaTemplate;


    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private AesUtil aesUtil;



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
            throw new UserServiceException(Status.GETRESOURCEERROR);
        }
        JSONObject object = JSON.parseObject(response);
        //获取openid
        String openid = object.getString("openid");
        String session = object.getString("session_key");

        if (openid==null||session== null){
            return response;
        }
        //将openid和session_key存入redis
        if(!redisUtil.hasKey(session)){
            //存入session一周
            redisUtil.set("msg"+openid,session);

        }else{
            throw new UserServiceException(Status.REGISTERERROR);
        }

        //信息存入数据库
        User user = userCache.getUserByOpenid(openid);
        //如果数据库存在用户信息，则重新发送jwt
        if (user != null){
            //验证手机号是否存在
            if (user.getPhone().equals(Integer.toString(0))){
                return jwtUtil.userJwtCreate(user.getUid(),false,user.getOpenid());
            }
            if (!user.getPhone().equals(Integer.toString(0))){
                return jwtUtil.userJwtCreate(user.getUid(),true,user.getOpenid());
            }
        }
        //如果不存在，则新建用户，重新插入数据
        Integer judge = userDao.insertOidPhone(openid, "0");

        try{
            Assert.state(judge.equals(1),Status.USERINSERTERROR.getMsg());
        }catch (Exception e){
            throw new UserServiceException(Status.USERINSERTERROR);
        }
        Integer uid = userCache.getUserByOpenid(openid).getUid();
        return jwtUtil.userJwtCreate(uid,false,openid);
    }



    @Override
    public String getFormService(String jwt) {
        //从数据库查找用户信息
        String uid = userJwtConfig.getPayload(jwt).get("uid");
        Dsign userinfo = signCache.getDsignByUid(Integer.parseInt(uid));
        try{
            Assert.notNull(userinfo,Status.GETFORMERROR.getMsg());
        }catch (Exception e){
            throw new UserServiceException(Status.GETFORMERROR);
        }
        return JSON.toJSONString(userinfo);
    }

    @Override
    public String getWebFormService(String jwt) {
        String uid = userWebJwtConfig.getPayload(jwt).get("uid");
        Dsign userinfo = signCache.getDsignByUid(Integer.parseInt(uid));
        try{
            Assert.notNull(userinfo,Status.GETFORMERROR.getMsg());
        }catch (Exception e){
            throw new UserServiceException(Status.GETFORMERROR);
        }
        return JSON.toJSONString(userinfo);
    }

    @Override
    public boolean setStatusService(String jwt, String request) {

        //解析json获取参数
        JSONObject jsonObject = JSON.parseObject(request);
        Integer check = jsonObject.getInteger("check");
        String reason = jsonObject.getString("reason");

        String uid = userJwtConfig.getPayload(jwt).get("uid");
       String openid = userCache.getUserByUid(Integer.parseInt(uid)).getOpenid();
        Integer integer =userCache.updateCheckQueByOid(check, reason, openid);
        return openid != null && integer.equals(1);
    }

    @Override
    public String getQrcodeService() {
        return qrCodeUtil.gengerateCode();
    }

    @Override
    public boolean loginByCodeService(String jwt,String uuid) {
            //检验二维码
        String uid = userJwtConfig.getPayload(jwt).get("uid");
        JSONObject jsonObject = JSON.parseObject(uuid);
        String newUuid = jsonObject.getString("uuid");
        return qrCodeUtil.checkCode(newUuid, uid);
    }

    @Override
    public String webLogin(String uuid) {
        if (redisUtil.hasKey(aesUtil.decrypt(uuid))){
            Qrcode qrcode = (Qrcode) redisUtil.get(aesUtil.decrypt(uuid));
            Integer uid = qrcode.getUid();
            boolean flag = false;
            String phone = userDao.getPhoneByUid(uid);
            String openid = userDao.getOpenidByUid(uid);
            int code = qrcode.getCode();
            if (!phone.equals(0)){
                flag = true;
            }
            if (code == 1){
                return jwtUtil.userWebJwtCreate(uid,flag,openid);
            }
        }
        throw new UserServiceException(Status.QRUUIDERROR);

    }

    @Override
    public String appLogin(String jwt) {
        try{
            //通过jwt获取uid
            String uid = userJwtConfig.getPayload(jwt).get("uid");
            String openid = userCache.getOpenidByUid(Integer.parseInt(uid));
            String phone = userCache.getUserByUid(Integer.parseInt(uid)).getPhone();
            if (phone.equals(Integer.toString(0))){
                return jwtUtil.userJwtCreate(Integer.parseInt(uid),false,openid);
            }
            if (!phone.equals(Integer.toString(0))){
                return jwtUtil.userJwtCreate(Integer.parseInt(uid),true,openid);
            }
        }catch (Exception e){
            throw new UserServiceException(Status.JWTUPDATEERROR);
        }
        return null;
    }



    @Override
    public String sign(String jwt, Dsign dsign,boolean app) {
        try{
            String uid;
            if (app){
                 uid = userJwtConfig.getPayload(jwt).get("uid");
            }else{
                 uid = userWebJwtConfig.getPayload(jwt).get("uid");
            }
            String openid = userCache.getUserByUid(Integer.parseInt(uid)).getOpenid();
            Map<String,Object> claims = new HashMap<>(8);
            claims.put("uid",uid);
            claims.put("phone",false);
            if (iSignDao.getSignByUid(Integer.parseInt(uid)) == null){
                Sign sign = new Sign(null,Integer.parseInt(uid),dsign.getName(),dsign.getGrade(),dsign.getCollege(),dsign.getMajor(),
                        dsign.getUserclass(),dsign.getDsp(),dsign.getDno(),dsign.getSecdno(),
                        dsign.getGender(),dsign.getSno(),dsign.getQq(),
                        dsign.getDomitory(),dsign.getKnow(),dsign.getParty());
                iSignDao.insertSign(sign);

            }else{
                signCache.updateDsignInformByUid(dsign,Integer.parseInt(uid));
            }
            if (app){
                return userJwtConfig.createJwt(claims, openid);
            }
            return userWebJwtConfig.createJwt(claims,openid);
        }catch (Exception e){
            throw new UserServiceException(Status.POSTAPPSIGNERROR);
        }
    }

    @Override
    public boolean getPhone(String jwt, String phone, boolean app) {
        try{
            kafkaTemplate.send("phone",phone);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public String checkCode(String jwt,String checkCode,String phone, boolean app) {
        String uid;
        if (app){
            uid = userJwtConfig.getPayload(jwt).get("uid");
        }else{
            uid = userWebJwtConfig.getPayload(jwt).get("uid");
        }
        String openid = userDao.getOpenidByUid(Integer.parseInt(uid));
        boolean judge = redisUtil.hasKey(phone);
        if (judge){
            String code = (String)redisUtil.get(phone);
            if (code.equals(checkCode)){
                userDao.updatePhoneByUid(phone,Integer.parseInt(uid));
                if (app){
                    return jwtUtil.userJwtCreate(Integer.parseInt(uid),true,openid);
                }else{
                    return jwtUtil.userWebJwtCreate(Integer.parseInt(uid),true,openid);
                }
            }
        }
        throw new UserServiceException(Status.CHECKPHONEERROR);


    }


}
