package com.gdutelc.snp.cache;

import com.gdutelc.snp.dao.IUserDao;
import com.gdutelc.snp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author kid
 */
@Component
@CacheConfig(cacheNames = "users")
public class UserCache implements IuserCache{
    @Autowired
    private IUserDao userDao;

    @Cacheable(key = "#openid")
    @Override
    public User getUserByOpenid(String openid) {
        return userDao.getUserByOpenid(openid);
    }

    @Cacheable(key = "getMethodName()")
    @Override
    public String getOpenidByUid(Integer uid) {
        return userDao.getOpenidByUid(uid);
    }

    @Cacheable(key = "result.openid")
    @Override
    public User getUserByUid(Integer uid) {
        return userDao.getUserByUid(uid);
    }
    @CacheEvict(key = "#openid")
    @Override
    public Integer updateCheckQueByOid(Integer check, String que, String openid) {
        return userDao.updateCheckQueByOid(check, que, openid);
    }
}
