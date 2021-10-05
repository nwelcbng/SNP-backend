package com.gdutelc.snp;

import com.gdutelc.snp.dao.IUserDao;
import com.gdutelc.snp.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDaoTests {
    @Autowired
    private IUserDao iUserDao;

    @Test
    @DisplayName("用户dao插入测试")
    void insertTest(){
        User user = new User();
        user.setOpenid("oPndU5eWqEkM2101oOOf_rWSM3Fk");
        user.setPhone("12344567888");
        iUserDao.insertOidPhone(user.getOpenid(),user.getPhone());
    }

    @Test
    @DisplayName("获取用户openid")
    void getOpenIdTest(){
        String openid = iUserDao.getOpenidByUid(1);
        System.out.println(openid);
    }

    @Test
    @DisplayName("获取User")
    void getUserTest(){
        User user = iUserDao.getUserByUid(1);
        System.out.println(user);
        System.out.println(user.getOpenid());
        System.out.println(user.getPhone());
    }
    @Test
    @DisplayName("通过uid获取手机号")
    void getPhoneTest(){
        String phone = iUserDao.getPhoneByUid(1);
        System.out.println(phone);
    }

    @Test
    @DisplayName("通过openid更新手机号")
    void updatePhoneTest(){
        Integer judge = iUserDao.updatePhoneByOpenid("1rrrrrrrr","11111111111");
        System.out.println(judge);
    }
    @Test
    @DisplayName("通过openid更改enroll和check")
    void updateEnrollCheckTest(){
    }
    @Test
    @DisplayName("通过openid更改result和que")
    void updateResultQueTest(){
    }

    @Test
    void getUserBy(){
        User user = iUserDao.getUserByOpenid("12354");
        System.out.println(user);
    }
    @Test
    void update(){
        Integer integer = iUserDao.updateEnrollByItself(201, 101);
        System.out.println(integer);
    }

}
