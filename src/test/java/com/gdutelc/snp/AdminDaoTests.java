package com.gdutelc.snp;

import com.gdutelc.snp.dao.IAdminDao;
import com.gdutelc.snp.entity.Admin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminDaoTests {
    @Autowired
    private IAdminDao iAdminDao;


    @Test
    @DisplayName("插入admin信息")
    void insertInformTest(){
        Admin admin = new Admin();
        Integer integer = iAdminDao.insertInform("qwer1235","kid",1,2,"123456","13428765420");
        System.out.println(integer);
    }

    @Test
    @DisplayName("通过admin插入")
    void insertInformByAdminTest(){
        Admin admin = new Admin();
        admin.setOpenid("a123123123");
        admin.setUsername("qqqqqqqqqq");
        admin.setAdno(4);
        admin.setPassword("qwert");
        admin.setPosition(5);
        admin.setPhone("wwwwwwwwwww");
        Integer judge = iAdminDao.insertInformByAdmin(admin);
        System.out.println(judge);
    }
    @Test
    @DisplayName("通过aid获取openid")
    void getOpenIdTest(){
        String openIdByAid = iAdminDao.getOpenIdByAid(2);
        System.out.println(openIdByAid);
    }
    @Test
    @DisplayName("通过aid获取admin")
    void getAdminTest(){
        Admin admin = iAdminDao.getAdminByAid(2);
        System.out.println(admin.getPassword());
    }
    @Test
    @DisplayName("通过aid获取password")
    void getPasswordTest(){
        String password = iAdminDao.getPassWordByAid(2);
        System.out.println(password);
    }

    @Test
    @DisplayName("通过openid更新password")
    void updatePasswordTest(){
        Integer judge = iAdminDao.updatePasswordByOpenid("asdfghjjkkl","11111");
        System.out.println(judge);
    }
    @Test
    @DisplayName("通过openid更新phone")
    void updatePhoneTest(){
        Integer judge = iAdminDao.updatePhoneByOpenid("asdfghjjkkl","11111");
        System.out.println(judge);
    }
    @Test
    @DisplayName("通过openid更新摊位")
    void updatePositionTest(){
        Integer judge = iAdminDao.updatePositionByOpenid("asdfghjjkkl",1);
        System.out.println(judge);
    }
    @Test
    @DisplayName("通过openid更新部门")
    void updateAndoTest(){
        Integer judge = iAdminDao.updatePositionByOpenid("asdfghjjkkl",12);
        System.out.println(judge);
    }

}
