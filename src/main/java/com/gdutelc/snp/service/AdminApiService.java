package com.gdutelc.snp.service;

import com.gdutelc.snp.dto.Dsign;

import java.util.List;

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

    /**
     * 通过性别获取用户信息
     * @param gender 性别
     * @return 符合条件的所有用户的信息
     */
    String getDsignByGender(Boolean gender);

    /**
     * 通过学院获取用户信息
     * @param college 学院
     * @return 符合条件的所有用户的信息
     */
    String getDsignByCollege(Integer college);

    /**
     * 通过意向部门获取用户信息
     * @param dno 意向部门
     * @return 符合条件的所有用户的信息
     */
    String getDsignByDno(Integer dno);

    /**
     * 确认新生通过面试
     * @param jwt 意向部门
     * @param uid 用户id
     * @param check 是否通过面试
     * @return 是否给新生发送信息
     */
    boolean confirm(String jwt, Integer uid, boolean check);
}
