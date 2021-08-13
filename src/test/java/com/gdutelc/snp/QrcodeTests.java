package com.gdutelc.snp;

import com.gdutelc.snp.util.QrCodeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QrcodeTests {
    @Autowired
    private QrCodeUtil qrCodeUtil;

    @Test
    public void test(){
        String uuid = qrCodeUtil.gengerateCode();
        System.out.println(uuid);
    }
}
