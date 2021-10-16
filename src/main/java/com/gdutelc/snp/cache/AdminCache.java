package com.gdutelc.snp.cache;
import com.gdutelc.snp.dao.IAdminDao;
import com.gdutelc.snp.entity.Admin;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author kid
 */
@Component
@CacheConfig(cacheNames = "admins")
@EnableCaching
public class AdminCache implements IadminCache {
    @Resource
    private IAdminDao iAdminDao;
    @Cacheable(key = "getMethodName()+#username",unless = "#result == null")
    @Override
    public String getPassowrdByUsername(String username) {
        return iAdminDao.getPassowrdByUsername(username);
    }

    @Cacheable(key = "getMethodName()+#username",unless = "#result == null")
    @Override
    public Admin getAdminByUsername(String username) {
        return iAdminDao.getAdminByUsername(username);
    }
    @CacheEvict(value = "admins",allEntries = true)
    @Override
    public Integer updatePasswordCache(String password, Integer uid) {
        return iAdminDao.updatePasswordByAid(password, uid);
    }
    @Cacheable(key = "getMethodName()+#aid",unless = "#result == null")
    @Override
    public String getAdminName(Integer aid) {
        return iAdminDao.getAdminByAid(aid).getUsername();
    }


}
