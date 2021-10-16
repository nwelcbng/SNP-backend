package com.gdutelc.snp.dao;


import com.gdutelc.snp.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author kid
 */
@Component
public interface IAdminDao {

    /**
     * 插入admin的信息
     *
     * @param openid openid
     * @param username 用户名
     * @param adno  部门
     * @param position  摊位
     * @param password  密码
     * @param phone 手机
     * @return  插入是否成功
     */
    Integer insertInform(String openid, String username, Integer adno,Integer position, String password, String phone);

    /**
     * 插入admin的信息
     *
     * @param admin admin
     * @return  插入是否成功
     */
    Integer insertInformByAdmin(Admin admin);

    /**
     * 通过aid获取openid
     *
     * @param aid aid
     * @return  插入是否成功
     */
    String getOpenIdByAid(Integer aid);

    /**
     * 通过username获取password
     *
     * @param username username
     * @return  获取密码
     */
    String getPassowrdByUsername(String username);


    /**
     * 通过aid获取管理员信息
     *
     * @param aid aid
     * @return  管理员内容
     */
    Admin getAdminByAid(Integer aid);

    /**
     * 通过username获取管理员信息
     *
     * @param username username
     * @return  管理员内容
     */
    Admin getAdminByUsername(String username);

    /**
     * 通过aid获取密码
     *
     * @param aid aid
     * @return  管理员内容
     */
    String getPassWordByAid(Integer aid);


    /**
     * 通过openid更新密码
     *
     * @param openid openid
     * @param password 密码
     * @return  成功条数
     */
    Integer updatePasswordByOpenid(String openid,String password);


    /**
     * 通过openid更新手机
     *
     * @param openid openid
     * @return  成功条数
     */
    Integer updatePhoneByOpenid(String openid,String phone);


    /**
     * 通过openid更新摊位
     *
     * @param openid openid
     * @return  成功条数
     */
    Integer updatePositionByOpenid(String openid,Integer position);

    /**
     * 通过openid更新部门
     *
     * @param openid openid
     * @return  成功条数
     */
    Integer updateAdnoByOpenid(String openid,Integer dno);

    Integer updatePasswordByAid(@Param("password") String password, @Param("aid") Integer aid);

}
