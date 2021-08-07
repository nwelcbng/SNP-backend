package com.gdutelc.snp;


import com.gdutelc.snp.dao.ISignDao;
import com.gdutelc.snp.dto.Dsign;
import com.gdutelc.snp.entity.Sign;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SignDaoTests {

    @Autowired
    private ISignDao signDao;

    @Test
    @DisplayName("报名信息插入")
    void insertSignTest(){
        Sign sign = new Sign(null,2,"wwwww",23,3,"电子科学与技术","四班", "hh", 3,4,true,"3119007324","123445556","东十三","heitai","no");
        Integer integer = signDao.insertSign(sign);
        System.out.println(integer);
    }
    @Test
    @DisplayName("查询sign")
    void getSignTest(){
//        Sign sign = new Sign(null,1,"kid",2,3,"电子科学与技术","四班", "hh", 3,4,true,"3119007324","123445556","东十四","heitai","no");
        Sign sign = signDao.getSignByUid(1);
        System.out.println(sign.getDomitory());
    }
    @Test
    @DisplayName("查询Dsign")
    void getDsignTest(){
//        Sign sign = new Sign(null,1,"kid",2,3,"电子科学与技术","四班", "hh", 3,4,true,"3119007324","123445556","东十四","heitai","no");
        Dsign sign = signDao.getDsignByUid(10);
        System.out.println(sign);
    }
    @Test
    @DisplayName("通过性别查询Dsign")
    void getDsignByGenderTest(){
//        Sign sign = new Sign(null,1,"kid",2,3,"电子科学与技术","四班", "hh", 3,4,true,"3119007324","123445556","东十四","heitai","no");
        List<Dsign> dsign = signDao.getDsignByGender(true);
        System.out.println(dsign.get(0).getDomitory());
        System.out.println(dsign.get(1).getDomitory());
    }
    @Test
    @DisplayName("通过选择的部门查询Dsign")
    void getDsignByDnoTest(){
//        Sign sign = new Sign(null,1,"kid",2,3,"电子科学与技术","四班", "hh", 3,4,true,"3119007324","123445556","东十四","heitai","no");
        List<Dsign> dsign = signDao.getDsignByDno(3);
        System.out.println(dsign.get(0).getDomitory());
        System.out.println(dsign.get(1).getDomitory());
    }
    @Test
    @DisplayName("通过uid更新Dsign")
    void updateDsignByDnoTest(){
      Dsign ign = new Dsign("小朋友",2,3,"电子科学与技术","四班", "hh", 3,4,false,"3119007324","123445556","东十四","heitai","no");
      Integer judge = signDao.updateDsignInformByUid(ign, 2);

    }


}
