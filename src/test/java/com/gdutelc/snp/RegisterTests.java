package com.gdutelc.snp;


import com.gdutelc.snp.service.impl.UserApiServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegisterTests {
    @Autowired
    private UserApiServiceImpl registerService;
    @Test
    void test(){
        registerService.registerService("111111");

    }
}
