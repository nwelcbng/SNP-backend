package com.gdutelc.snp.cache;

import com.gdutelc.snp.dao.IUserDao;
import com.gdutelc.snp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author kid
 */
@Component
@CacheConfig(cacheNames = "users")
@EnableCaching
public class UserCache implements IuserCache{
    private final IUserDao userDao;
    @Autowired
    public UserCache(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Cacheable(key = "getMethodName()+#openid", unless = "#result == null"  )
    @Override
    public User getUserByOpenid(String openid) {
        return userDao.getUserByOpenid(openid);
    }

    @Cacheable(key = "getMethodName()+#uid", unless = "#result == null" )
    @Override
    public String getOpenidByUid(Integer uid) {
        return userDao.getOpenidByUid(uid);
    }

    @Cacheable(key = "getMethodName()+#uid", unless = "#result == null" )
    @Override
    public User getUserByUid(Integer uid) {
        return userDao.getUserByUid(uid);
    }


    @Cacheable(key = "getMethodName()+#uid", unless = "#result == null" )
    @Override
    public String getPhoneByUid(Integer uid) {
        return userDao.getPhoneByUid(uid);
    }

    @CacheEvict(value = "users",allEntries = true)
    @Override
    public Integer updateCheckQueByOid(Integer check, String que, String openid) {
        return userDao.updateCheckQueByOid(check, que, openid);
    }

    @CacheEvict(value = "users",allEntries = true)
    @Override
    public Integer updateEnrollByUid(Integer enroll, Integer uid) {
        return userDao.updateEnrollByUid(enroll, uid);
    }

    @CacheEvict(value = "users",allEntries = true)
    @Override
    public Integer closeSign(Integer enroll) {
        return userDao.closeSign(enroll);
    }

    @CacheEvict(value = "users",allEntries = true)
    @Override
    public Integer updateEnroll(Integer enroll, Integer check) {
        return userDao.updateEnroll(enroll, check);

    }
    @CacheEvict(value = "users",allEntries = true)
    @Override
    public Integer updateEnrollByitself(Integer oldEnroll, Integer newEnroll) {
        return userDao.updateEnrollByItself(oldEnroll, newEnroll);
    }


    @Override
    public List<User> getUserByEnroll(Integer enroll) {
        return null;
    }
    @CacheEvict(value = "users",allEntries = true)
    @Override
    public Integer updateResultByUidCache(String result, Integer uid) {
        return userDao.updateResultByUid(result, uid);
    }

    @CacheEvict(value = "users",allEntries = true)
    @Override
    public Integer changeSatusCache(Integer oldEnroll) {
        return userDao.changeStatus(oldEnroll);
    }
    @CacheEvict(value = "users",allEntries = true)
    @Override
    public Integer updatePhoneByUid(String phone, Integer uid) {
        return userDao.updatePhoneByUid(phone, uid);
    }
}
