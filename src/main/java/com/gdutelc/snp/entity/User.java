package com.gdutelc.snp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kid
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer uid;
    private String openid;
    private String phone;
    private Integer enroll;
    private Integer check;
    private String  result;
    private String  que;


}
