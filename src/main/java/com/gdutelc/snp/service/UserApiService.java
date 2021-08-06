package com.gdutelc.snp.service;

import org.springframework.stereotype.Component;

/**
 * @author kid
 */


public interface UserApiService {

    /**
     * 获取code返回userjwt
     * @param   code  前端发送的code
     * @return  生成的userjwt
     */
    public String registerService(String code);


    /**
     * 获取用户手机号
     * @param   jwt  前端发送的jwt
     * @param   request 前端发来的json
     * @return  生成的userjwt
     */
    public boolean getPhoneService(String jwt,String request);

}
