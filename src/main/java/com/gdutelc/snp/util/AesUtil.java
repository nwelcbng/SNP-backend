package com.gdutelc.snp.util;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author kid
 */

@Component
public class AesUtil {
    @Value("${config.aes.key}")
    private  String key;
    private final String  algorithm = "AES/ECB/PKCS5Padding";

    /**
     * AES加密
     *
     * @param data 要加密的数据
     * @return 返回Base64转码后的加密数据
     */
    public String encrypt(String data) {
        //创建密码器

        try {

            Cipher cipher = Cipher.getInstance(algorithm);
            byte[] newData = data.getBytes(StandardCharsets.UTF_8);

            cipher.init(Cipher.ENCRYPT_MODE,getSecretKey());
            byte[] result = cipher.doFinal(newData);
            return Base64.encodeBase64String(result);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * AES解密

     * @param encrypted
     *         已加密的密文
     * @return 返回解密后的数据
     */
    public  String decrypt(String encrypted) {
        try {

            //实例化
            Cipher cipher = Cipher.getInstance(this.algorithm);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());

            //执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(result, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }

        //使用密钥初始化，设置为解密模式

    }
    /**
     * 生成加密秘钥
     *
     * @return secretly
     */
    public SecretKeySpec getSecretKey(){
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(this.key.getBytes());
            kg.init(128,random);
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(),"AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

}
