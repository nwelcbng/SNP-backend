package com.gdutelc.snp.service.impl;
import com.alibaba.fastjson.JSON;
import com.gdutelc.snp.cache.AdminCache;
import com.gdutelc.snp.cache.SignCache;
import com.gdutelc.snp.config.jwt.AdminJwtConfig;
import com.gdutelc.snp.dto.Dsign;
import com.gdutelc.snp.exception.AdminErrorException;
import com.gdutelc.snp.exception.GetFormErrorException;
import com.gdutelc.snp.service.AdminApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kid
 */
@Service
public class AdminApiServiceImpl implements AdminApiService {
    @Autowired
    AdminJwtConfig adminJwtConfig;

    @Autowired
    private AdminCache adminCache;

    @Autowired
    private SignCache signCache;


    @Override
    public String adminLogin(String username, String password) {
        String passowrdByUsername = adminCache.getPassowrdByUsername(username);

        if (passowrdByUsername == null){
            throw new AdminErrorException("数据库获取密码失败");
        }
        if (!password.equals(passowrdByUsername)){
            throw new AdminErrorException("密码错误");
        }
        Map<String,Object> data = new HashMap<>(4);
        data.put("username",username);
        return adminJwtConfig.createJwt(data, password);
    }

    @Override
    public String getDsignByGender(Boolean gender) {
        List<Dsign> dsigns = signCache.getDsignByGender(gender);
        if (dsigns == null){
            throw new GetFormErrorException("获取表单信息失败");
        }
        return JSON.toJSONString(dsigns);
    }

    @Override
    public String getDsignByCollege(Integer college) {
        List<Dsign> dsigns = signCache.getDsignByCollege(college);
        if (dsigns == null){
            throw new GetFormErrorException("获取表单信息失败");
        }
        return JSON.toJSONString(dsigns);
    }

    @Override
    public String getDsignByDno(Integer dno) {
        List<Dsign> dsigns = signCache.getDsignByDno(dno);
        if (dsigns == null){
            throw new GetFormErrorException("获取表单信息失败");
        }
        return JSON.toJSONString(dsigns);
    }
}
