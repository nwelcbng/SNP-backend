package com.gdutelc.snp.cache;

import com.gdutelc.snp.dao.ISignDao;
import com.gdutelc.snp.dto.Dsign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @author kid
 */
@CacheConfig(cacheNames = "signs")
@Component
@EnableCaching
public class SignCache implements IsignCache,Serializable{
    @Autowired
    private ISignDao signDao;

    @Cacheable(key = "getMethodName()",unless = "#result == null" )
    @Override
    public List<Dsign> getDsignByGender(Boolean gender) {
        return signDao.getDsignByGender(gender);
    }

    @Cacheable(key = "getMethodName()",unless = "#result == null" )
    @Override
    public List<Dsign> getDsignByCollege(Integer college) {
        return signDao.getDsignByCollege(college);
    }
    @Cacheable(key = "getMethodName()",unless = "#result == null" )
    @Override
    public List<Dsign> getDsignByDno(Integer dno) {
        return signDao.getDsignByDno(dno);
    }

    @CacheEvict(value = "signs",allEntries = true)
    @Override
    public Integer updateDsignInformByUid(Dsign dsign, Integer uid) {
        System.out.println(dsign);
        System.out.println(uid);
        return signDao.updateDsignInformByUid(dsign, uid);
    }

    @Cacheable(key = "#result.name",unless = "#result == null")
    @Override
    public Dsign getDsignByUid(Integer uid) {
        return  signDao.getDsignByUid(uid);
    }

}
