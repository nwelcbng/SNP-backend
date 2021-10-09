package com.gdutelc.snp.util;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author kid
 */
@Component
public class CodeUtil {
    public String newCode(){
        StringBuilder str =  new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}
