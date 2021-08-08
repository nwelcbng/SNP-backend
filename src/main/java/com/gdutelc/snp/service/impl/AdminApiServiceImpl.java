package com.gdutelc.snp.service.impl;

import com.gdutelc.snp.config.jwt.AdminJwtConfig;
import com.gdutelc.snp.dao.IAdminDao;
import com.gdutelc.snp.exception.AdminErrorException;
import com.gdutelc.snp.service.AdminApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kid
 */
@Service
public class AdminApiServiceImpl implements AdminApiService {
    @Autowired
    private IAdminDao adminDao;
    @Autowired
    AdminJwtConfig adminJwtConfig;


    @Override
    public String adminLogin(String username, String password) {
        String passowrdByUsername = adminDao.getPassowrdByUsername(username);

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
}
