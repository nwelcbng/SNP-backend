package com.gdutelc.snp.cache;

import com.gdutelc.snp.dao.ISignDao;
import com.gdutelc.snp.dto.Dsign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author kid
 */
@CacheConfig(cacheNames = "signs")
@Component
public class SignCache implements IsignCache{
    @Autowired
    private ISignDao signDao;
    @Cacheable(key = "getMethodName()")
    @Override
    public List<Dsign> getDsignByGender(Boolean gender) {
        return signDao.getDsignByGender(gender);
    }
    @Cacheable(key = "getMethodName()")
    @Override
    public List<Dsign> getDsignByCollege(Integer college) {
        return signDao.getDsignByCollege(college);
    }
    @Cacheable(key = "getMethodName()")
    @Override
    public List<Dsign> getDsignByDno(Integer dno) {
        return signDao.getDsignByDno(dno);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = {"signs"},key = "'getDsignByGender'"),
                    @CacheEvict(value = {"signs"},key = "'getDsignByCollege'"),
                    @CacheEvict(value = {"signs"},key = "'getDsignByDno'"),
                    @CacheEvict(value = {"signs"},key = "'getDsignByUid'")
            }
    )
    @Override
    public Integer updateDsignInformByUid(Dsign dsign, Integer uid) {
        return updateDsignInformByUid(dsign, uid);
    }

    @Cacheable(key = "getMethodName()")
    @Override
    public Dsign getDsignByUid(Integer uid) {
        return  signDao.getDsignByUid(uid);
    }
}
