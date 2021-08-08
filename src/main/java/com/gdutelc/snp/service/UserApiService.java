package com.gdutelc.snp.service;


/**
 * @author kid
 */


public interface UserApiService {

    /**
     * 获取code返回jwt
     * @param   code  前端发送的code
     * @return  生成的jwt
     */
    String registerService(String code);


    /**
     * 后台获取用户手机号
     * @param   jwt  前端发送的jwt
     * @param   request 前端发来的json
     * @return  是否获取成功
     */
    boolean getPhoneService(String jwt,String request);
    /**
     * 前端获取用户表单
     * @param   jwt  前端发送的jwt
     * @return  返回表单json数据
     */
    String getFormService(String jwt);


    /**
     * 后台存入用户状态
     * @param   jwt  前端发送的jwt
     * @param  request 前端发的json数据
     * @return  返回表单json数据
     */
    boolean setStatusService(String jwt,String request);


}
