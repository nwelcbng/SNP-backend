package com.gdutelc.snp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gdutelc.snp.config.jwt.UserJwtConfig;
import com.gdutelc.snp.dao.IUserDao;
import com.gdutelc.snp.exception.JwtErrorException;
import com.gdutelc.snp.exception.RegisterErrorException;
import com.gdutelc.snp.service.UserApiService;
import com.gdutelc.snp.util.RedisUtil;
import com.github.kevinsawicki.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    private RedisUtil redisUtil;

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
        if (openid.isEmpty()||session.isEmpty()){
            throw new RegisterErrorException("获取openid内容或session_key失败");
        }
        //信息存入数据库
        Integer judge = userDao.insertOidPhone(openid, "0");
        if (!judge.equals(1)){
            throw new RegisterErrorException("无法往数据库存入user信息");
        }
        //将openid和session_key存入redis
        if(!redisUtil.hasKey(session)){
            redisUtil.set(openid,session,0);
        }
        Integer uid = userDao.getUserByOpenid(openid).getUid();
        Map<String,Object> claims = new HashMap<String,Object>(4);
        claims.put("uid",uid);
        String jwt = userJwtConfig.createJwt(claims, openid);
        if (jwt.isEmpty()){
            throw new JwtErrorException("jwt没有正常生成");

        }
        return jwt;
    }

    @Override
    public boolean getPhoneService(String jwt, String request) {
        //获取需要的openid session_key
        JSONObject jsonObject = JSON.parseObject(request);
        String iv = jsonObject.getString("iv");
        String encryptedData = jsonObject.getString("encryptedData");
        Map<String, String> payload = userJwtConfig.getPayload(jwt);
        String uid = payload.get("uid");
        String openid = userDao.getOpenidByUid(Integer.parseInt(uid));
        String session = redisUtil.get(openid);
        //TODO 获取手机号的算法未知hhh
        return false;
    }
}
