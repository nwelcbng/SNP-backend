package com.gdutelc.snp.cache;

import com.gdutelc.snp.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author kid
 */
@Component
public interface IuserCache {

    /**
     * 获取user
     *
     * @param openid openid
     * @return 查到的用户
     */
    User getUserByOpenid(String openid);

    /**
     * 获取openid
     *
     * @param uid uid
     * @return 查到的openid
     */
    String getOpenidByUid(Integer uid);

    /**
     * 获取用户
     *
     * @param uid uid
     * @return 查到的用户
     */
    User getUserByUid(Integer uid);

    /**
     * 更新部长评价和个人缺勤原因
     * @param check 个人确认是否参加
     * @param que  个人缺勤原因
     * @param openid openid
     * @return 升级条数
     */
    Integer updateCheckQueByOid(Integer check, String que,String openid);



}
