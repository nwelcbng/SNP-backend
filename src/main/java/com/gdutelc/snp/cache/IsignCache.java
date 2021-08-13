package com.gdutelc.snp.cache;

import com.gdutelc.snp.dto.Dsign;

import java.util.List;

/**
 * @author kid
 */
public interface IsignCache {
    /**
     * 通过性别获取用户信息
     * @param   gender  性别
     * @return  用户报名信息
     */
    List<Dsign> getDsignByGender(Boolean gender);

    /**
     * 通过学院获取用户信息
     * @param college 学院
     * @return 用户报名信息
     */
    List<Dsign> getDsignByCollege(Integer college);
    /**
     * 通过意向部门获取用户信息
     * @param   dno  意向部门
     * @return  用户报名信息
     */
    List<Dsign> getDsignByDno(Integer dno);

    Integer updateDsignInformByUid(Dsign dsign,Integer uid);

}
