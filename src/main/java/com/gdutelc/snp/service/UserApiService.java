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
    String webLogin(String uuid);

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
    String sign(String jwt, Dsign dsign,boolean app);


    /**
     * 获取手机
     * @param jwt
     * @param phone
     * @param app
     * @return  返回是否成功获取
     */
    boolean getPhone(String jwt, String phone, boolean app);


    /**
     * 检验验证码
     * @param jwt
     * @param checkCode
     * @param app
     * @param phone
     * @return  返回新的jwt
     */
    String checkCode(String jwt,String checkCode,String phone,boolean app);






}
