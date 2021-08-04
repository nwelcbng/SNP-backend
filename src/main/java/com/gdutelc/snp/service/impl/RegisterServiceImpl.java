package com.gdutelc.snp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gdutelc.snp.config.jwt.UserJwtConfig;
import com.gdutelc.snp.dao.IUserDao;
import com.gdutelc.snp.service.RegisterService;
import com.github.kevinsawicki.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kid
 */

@Service
public class RegisterServiceImpl implements RegisterService {
    @Value("${config.register.appid}")
    private String appid;
    @Value("${config.register.secret}")
    private String secret;

    @Autowired
    private UserJwtConfig userJwtConfig;
    @Autowired
    private IUserDao userDao;

    @Override
    public String registerService(String code) {
        Map<String,String> data = new HashMap<>(8);
        data.put("appid", this.appid);
        data.put("secret", this.secret);
        data.put("js_code", code);
        data.put("grant_type", "authorization_code");
        String response = HttpRequest.get("https://api.weixin.qq.com/sns/jscode2session").form(data).body();
        JSONObject object = JSON.parseObject(response);
        String openid = object.getString("openid");
        //TODO 如何获取手机？
        //TODO 生成jwt返回
        return null;

    }
}
