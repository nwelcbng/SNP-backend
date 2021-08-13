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
import com.gdutelc.snp.service.UserApiService;
import com.gdutelc.snp.util.QrCodeUtil;
import com.gdutelc.snp.util.RedisUtil;
import com.github.kevinsawicki.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private UserCache userCache;

    @Autowired
    private SignCache signCache;



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

        if (openid==null||session== null){
            return response;
        }
        //将openid和session_key存入redis
        if(!redisUtil.hasKey(session)){
            //存入session一周
            redisUtil.set(openid,session,3600*24*7);

        }else{
            throw new RegisterErrorException("该用户的session已过期");
        }

        //信息存入数据库
        User user = userCache.getUserByOpenid(openid);
        //如果数据库存在用户信息，则重新发送jwt
        if (user != null){
            Integer uid = user.getUid();
            Map<String,Object> claims = new HashMap<>(4);
            claims.put("uid",uid);
            return userJwtConfig.createJwt(claims,openid);
        }
        //如果不存在，则新建用户，重新插入数据
        Integer judge = userDao.insertOidPhone(openid, "0");
        if (!judge.equals(1)){
            throw new RegisterErrorException("无法往数据库存入user信息");
        }

        Integer uid = userCache.getUserByOpenid(openid).getUid();
        Map<String,Object> claims = new HashMap<>(4);
        claims.put("uid",uid);
        return userJwtConfig.createJwt(claims, openid);

    }



    @Override
    public String getFormService(String jwt) {
        //从数据库查找用户信息
        String uid = userJwtConfig.getPayload(jwt).get("uid");
        Sign userinfo = iSignDao.getSignByUid(Integer.parseInt(uid));
        if (userinfo == null){
            throw new GetFormErrorException("获取表单信息失败");
        }
        return JSON.toJSONString(userinfo);
    }

    @Override
    public String getWebFormService(String jwt) {
        String uid = userWebJwtConfig.getPayload(jwt).get("uid");
        Sign userinfo = iSignDao.getSignByUid(Integer.parseInt(uid));
        if (userinfo == null){
            throw new GetFormErrorException("获取表单信息失败");
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
        try{
            //检验二维码
            String uid = userJwtConfig.getPayload(jwt).get("uid");
            qrCodeUtil.checkCode(uuid, uid);
            return true;
        }catch(Exception e){
            throw new QrCodeErrorException("二维码检验失败");
        }

    }

    @Override
    public String webLogin() {
        try {
            List<String> uuidList = (List<String>)redisUtil.get("uuid");
            if(uuidList.isEmpty()){
                return null;
            }
            int size = uuidList.size();
            int flag = 0;
            for(int i = 0; i<size; i++){
                String uuid = uuidList.get(i);
                Qrcode qrcode = (Qrcode) redisUtil.get(uuid);
                int code = qrcode.getCode();
                if(code == 1){
                    flag = i;
                    break;
                }


            }
            String find = uuidList.get(flag);
            Qrcode code = (Qrcode) redisUtil.get(find);
            Integer uid = code.getUid();
            String openid = userCache.getOpenidByUid(uid);
            Map<String,Object> claims = new HashMap<>(4);
            claims.put("uid",uid);
            uuidList.remove(find);
            redisUtil.set("uuid",uuidList,180);
            redisUtil.del(find);
            return userWebJwtConfig.createJwt(claims, openid);
        }catch (Exception e){
            throw new QrCodeErrorException("扫码登录失败");
        }
    }

    @Override
    public String appLogin(String jwt) {
        try{
            String uid = userJwtConfig.getPayload(jwt).get("uid");
            String openid = userCache.getOpenidByUid(Integer.parseInt(uid));
            Map<String,Object> claims = new HashMap<>(4);
            claims.put("uid",uid);
            return userJwtConfig.createJwt(claims,openid);
        }catch (Exception e){
            throw new JwtErrorException("JWT更新失败");
        }
    }



    @Override
    public boolean sign(String jwt, Dsign dsign,boolean app) {
        try{
            String uid;
            if (app){
                 uid = userJwtConfig.getPayload(jwt).get("uid");
            }else{
                 uid = userWebJwtConfig.getPayload(jwt).get("uid");
            }
            if (iSignDao.getSignByUid(Integer.parseInt(uid)) == null){
                Sign sign = new Sign(null,Integer.parseInt(uid),dsign.getName(),dsign.getGrade(),dsign.getCollege(),dsign.getMajor(),
                        dsign.getUserclass(),dsign.getDsp(),dsign.getDno(),dsign.getSecdno(),
                        dsign.getGender(),dsign.getSno(),dsign.getQq(),
                        dsign.getDomitory(),dsign.getKnow(),dsign.getParty());
                iSignDao.insertSign(sign);

            }else{
                signCache.updateDsignInformByUid(dsign,Integer.parseInt(uid));
            }
            return true;
        }catch (Exception e){
            throw new SignPushErrorException("小程序端传入信息失败");
        }
    }



}
