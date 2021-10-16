package com.gdutelc.snp.cache;

import com.gdutelc.snp.dao.ISignDao;
import com.gdutelc.snp.dto.Dsign;
import com.gdutelc.snp.entity.Sign;
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

    @Cacheable(key = "getMethodName()",unless = "#result.isEmpty()" )
    @Override
    public List<Dsign> getDsignByGender(Boolean gender) {
        return signDao.getDsignByGender(gender);
    }

    @Cacheable(key = "getMethodName()",unless = "#result.isEmpty()" )
    @Override
    public List<Dsign> getDsignByCollege(Integer college) {
        System.out.println(college);
        List<Dsign> dsignByCollege = signDao.getDsignByCollege(college);
        System.out.println(dsignByCollege);
        return dsignByCollege;

    }
    @Cacheable(key = "getMethodName()",unless = "#result.isEmpty()" )
    @Override
    public List<Dsign> getDsignByDno(Integer dno) {
        return signDao.getDsignByDno(dno);
    }

    @CacheEvict(value = "signs",allEntries = true)
    @Override
    public Integer updateDsignInformByUid(Dsign dsign, Integer uid) {

        return signDao.updateDsignInformByUid(dsign, uid);
    }

    @Cacheable(key = "getMethodName()+#uid",unless = "#result == null")
    @Override
    public Dsign getDsignByUidCache(Integer uid) {
        return  signDao.getDsignByUid(uid);
    }

}
