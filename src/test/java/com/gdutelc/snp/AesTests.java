package com.gdutelc.snp;

import com.gdutelc.snp.util.AesUtil;
import com.gdutelc.snp.util.QrCodeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AesTests {
    @Autowired
    private AesUtil aesUtil;

    @Autowired
    private QrCodeUtil qrCodeUtil;

    @Test
    public void test(){
        String data = qrCodeUtil.gengerateCode();
        System.out.println(data);
        String newData = aesUtil.encrypt(data);
        System.out.println(newData);
        String decrypt = aesUtil.decrypt(newData);
        System.out.println(decrypt);
    }
}
