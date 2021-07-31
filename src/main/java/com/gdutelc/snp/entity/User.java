package com.gdutelc.snp.entity;


import lombok.Data;

/**
 * @author kid
 */
@Data
public class User {
    private Integer uid;
    private String openid;
    private String phone;
    private Integer enroll;
    private Integer check;
    private String  result;
    private String  que;
}
