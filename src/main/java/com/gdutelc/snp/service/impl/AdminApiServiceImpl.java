package com.gdutelc.snp.service.impl;
import com.alibaba.fastjson.JSON;
import com.gdutelc.snp.cache.AdminCache;
import com.gdutelc.snp.cache.SignCache;
import com.gdutelc.snp.config.jwt.AdminJwtConfig;
import com.gdutelc.snp.dto.Dsign;
import com.gdutelc.snp.exception.AdminServiceException;
import com.gdutelc.snp.result.Status;
import com.gdutelc.snp.service.AdminApiService;
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
public class AdminApiServiceImpl implements AdminApiService {
    @Resource
    AdminJwtConfig adminJwtConfig;

    @Resource
    private AdminCache adminCache;

    @Resource
    private SignCache signCache;


    @Override
    public String adminLogin(String username, String password) {
        String passowrdByUsername = adminCache.getPassowrdByUsername(username);
        try{
            Assert.notNull(passowrdByUsername, Status.GETPASSWORDERROR.getMsg());
        }catch (Exception e){
            throw new AdminServiceException(Status.GETPASSWORDERROR);
        }

        if (!password.equals(passowrdByUsername)){
            throw new AdminServiceException(Status.PASSWORDERROR);

        }
        Map<String,Object> data = new HashMap<>(4);
        data.put("username",username);
        return adminJwtConfig.createJwt(data, password);
    }

    @Override
    public String getDsignByGender(Boolean gender) {
        List<Dsign> dsigns = signCache.getDsignByGender(gender);
        try{
            Assert.notNull(dsigns,Status.GETFORMERROR.getMsg());
        }catch (Exception e){
            throw new AdminServiceException(Status.GETFORMERROR);
        }
        return JSON.toJSONString(dsigns);
    }

    @Override
    public String getDsignByCollege(Integer college) {
        List<Dsign> dsigns = signCache.getDsignByCollege(college);
        try{
            Assert.notNull(dsigns,Status.GETFORMERROR.getMsg());
        }catch (Exception e){
            throw new AdminServiceException(Status.GETFORMERROR);
        }
        return JSON.toJSONString(dsigns);
    }

    @Override
    public String getDsignByDno(Integer dno) {
        List<Dsign> dsigns = signCache.getDsignByDno(dno);
        try {
            Assert.notNull(dsigns, Status.GETFORMERROR.getMsg());
        }catch (Exception e){
            throw new AdminServiceException(Status.GETFORMERROR);
        }
        return JSON.toJSONString(dsigns);
    }
}
