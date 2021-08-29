package com.gdutelc.snp.service;


import com.gdutelc.snp.dto.Dsign;

import java.util.Map;

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
     * 前端获取用户表单
     * @param   jwt  前端发送的jwt
     * @return  返回表单json数据
     */
    String getFormService(String jwt);
    /**
     * 前端通过网页获取用户表单
     * @param   jwt  前端发送的jwt
     * @return  返回表单json数据
     */
    String getWebFormService(String jwt);


    /**
     * 后台存入用户状态
     * @param   jwt  前端发送的jwt
     * @param  request 前端发的json数据
     * @return  返回表单json数据
     */
    boolean setStatusService(String jwt,String request);

    /**
     * 后台发送二维码
     * @return  返回字符串
     */
    String getQrcodeService();


    /**
     * 后台获取用户信息确认登录
     * @param jwt jwt
     * @param uuid uuid
     * @return  确认是否成功
     */
    boolean loginByCodeService(String jwt,String uuid);

    /**
     * web前端轮询获取网页jwt
     * @return  确认是否成功
     */
    Map<Integer,String> webLogin();

    /**
     * 小程序前端每次登录获取更新的jwt
     * @param jwt
     * @return  确认是否成功
     */
    String appLogin(String jwt);

    /**
     * 小程序端报名
     * @param jwt
     * @param dsign
     * @param app
     * @return  返回jwt
     */
    String sign(String jwt, Dsign dsign,boolean app,String phone);

    /**
     * 验证码校验
     * @param cookie
     * @param code
     * @param app
     * @return  返回jwt
     */
    String checkPhone(String cookie,String code, boolean app);





}
