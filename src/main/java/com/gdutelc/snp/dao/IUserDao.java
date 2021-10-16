package com.gdutelc.snp.dao;
import com.gdutelc.snp.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author kid
 */
@Component
public interface IUserDao {
    /**
     * 插入openid和phone
     *
     * @param openid openid
     * @param phone 密码
     * @return  插入是否成功
     */
    Integer insertOidPhone(String openid,String phone);




    /**
     * 获取openid
     *
     * @param uid uid
     * @return openid
     */
    String getOpenidByUid(Integer uid);
    /**
     * 获取user
     *
     * @param uid uid
     * @return 查到的用户
     */
    User getUserByUid(Integer uid);
    /**
     * 获取user
     *
     * @param openid openid
     * @return 查到的用户
     */
    User getUserByOpenid(String openid);

    /**
     * 通过uid获取手机号
     *
     * @param uid uid
     * @return 查到的用户手机号
     */
    String getPhoneByUid(Integer uid);


    /**
     * 更新手机号
     *
     * @param openid openid
     * @param phone  手机
     * @return 升级条数
     */
    Integer updatePhoneByOpenid(String openid,String phone);

    /**
     * 修改用户信息
     * @param   phone 手机号
     * @param  uid  uid
     * @return  用户报名信息
     */

    Integer updatePhoneByUid(@Param("phone") String phone, @Param("uid") Integer uid);
    /**
     * 修改用户信息
     * @param   enroll 状态
     * @param  uid  uid
     * @return  用户报名信息
     */
    Integer updateEnrollByUid(@Param("enroll") Integer enroll, @Param("uid") Integer uid);


    /**
     * 更新录取状态和个人检查收到
     * @param enroll 录取状态
     * @param result 部长评价
     * @param openid openid
     * @return 升级条数
     */
    Integer updateEnrollResultByOid(Integer enroll, String result,String openid);


    /**
     * 更新部长评价和个人缺勤原因
     * @param check 个人确认是否参加
     * @param que  个人缺勤原因
     * @param openid openid
     * @return 升级条数
     */

    Integer updateCheckQueByOid(Integer check, String que,String openid);

    /**
     * 禁止学生进行报名
     * @param enroll 符合要求的状态
     * @return 被冻结的条数
     */
    Integer closeSign(Integer enroll);

    /**
     * 统一修改学生状态
     * @param enroll 符合要求的状态
     * @param  check 是否被冻结
     * @return 被冻结的条数
     */
    Integer updateEnroll(Integer enroll,Integer check);

    /**
     * 统一修改学生状态
     * @param oldEnroll 之前的状态
     * @param  newEnroll 新的状态
     * @return 被冻结的条数
     */
    Integer updateEnrollByItself(Integer oldEnroll,Integer newEnroll);

    List<User> getAllUser();

    Integer updateResultByUid(@Param("result") String result,@Param("uid") Integer uid);

    Integer changeStatus(Integer oldEnroll);















}
