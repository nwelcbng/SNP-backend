package com.gdutelc.snp.dao;
import com.gdutelc.snp.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

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
     * 更新录取状态和个人检查收到
     * @param enroll 录取状态
     * @param check  个人检查收到状态
     * @param openid openid
     * @return 升级条数
     */
    Integer updateEnrollCheckByOid(Integer enroll, Integer check,String openid);


    /**
     * 更新部长评价和个人缺勤原因
     * @param result 部长评价
     * @param que  个人缺勤原因
     * @param openid openid
     * @return 升级条数
     */

    Integer updateResultQueByOid(String result, String que,String openid);














}
