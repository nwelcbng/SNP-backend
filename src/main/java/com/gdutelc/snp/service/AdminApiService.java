package com.gdutelc.snp.service;

/**
 * @author kid
 */
public interface AdminApiService {

    /**
     * 管理登录
     * @param   username  管理员用户名
     * @param password   管理员密码
     * @return  生成的adminjwt
     */
    String adminLogin(String username,String password);


}
