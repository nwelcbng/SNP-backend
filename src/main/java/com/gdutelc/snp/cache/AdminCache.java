package com.gdutelc.snp.cache;

import com.gdutelc.snp.dao.IAdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author kid
 */
@Component
@CacheConfig(cacheNames = "admins")
public class AdminCache implements IadminCache{
    @Autowired
    private IAdminDao iAdminDao;
    @Cacheable(key = "#username")
    @Override
    public String getPassowrdByUsername(String username) {
        return iAdminDao.getPassowrdByUsername(username);
    }


}
