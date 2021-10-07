package com.gdutelc.snp.dao;

import com.gdutelc.snp.dto.Dsign;
import com.gdutelc.snp.entity.Sign;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author kid
 */
@Component
public interface ISignDao {


    /**
     * 通过sign插入用户信息
     *
     * @param sign  用户报名信息
     * @return  成功条数
     */
    Integer insertSign(Sign sign);


    /**
     * 通过uid查看用户信息
     * @param uid   uid
     * @return  用户报名信息
     */
    Sign getSignByUid(Integer uid);
    /**
     * 通过uid查看用户信息
     * @param uid   uid
     * @return  封装的用户报名信息
     */
    Sign getDsignByUid(Integer uid);



    /**
     * 通过性别获取用户信息
     * @param   gender  性别
     * @return  用户报名信息
     */
    List<Sign> getDsignByGender(Boolean gender);

    /**
     * 通过学院获取用户信息
     * @param college 学院
     * @return 用户报名信息
     */
    List<Sign> getDsignByCollege(Integer college);
    
    /**
     * 通过意向部门获取用户信息
     * @param   dno  意向部门
     * @return  用户报名信息
     */
    List<Sign> getDsignByDno(Integer dno);


    /**
     * 修改用户信息
     * @param   dsign  意向部门
     * @param uid   uid
     * @return  用户报名信息
     */
    Integer updateDsignInformByUid(@Param("dsign") Dsign dsign, @Param("uid") Integer uid);

    Integer checkForm(Integer uid);





}
