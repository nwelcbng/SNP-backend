package com.gdutelc.snp.cache;
import com.gdutelc.snp.dao.IAdminDao;
import com.gdutelc.snp.entity.Admin;
import org.springframework.cache.annotation.CacheConfig;
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
public class AdminCache implements IadminCache{
    @Resource
    private IAdminDao iAdminDao;
    @Cacheable(key = "#username",unless = "#result == null")
    @Override
    public String getPassowrdByUsername(String username) {
        return iAdminDao.getPassowrdByUsername(username);
    }

    @Cacheable(key = "#username",unless = "#result == null")
    @Override
    public Admin getAdminByUsername(String username) {
        return iAdminDao.getAdminByUsername(username);
    }


}
