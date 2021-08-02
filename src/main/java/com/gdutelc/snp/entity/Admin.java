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
public class Admin {
    private Integer aid;
    private String openid;
    private String username;
    private Integer adno;
    private Integer position;
    private String password;
    private String phone;
}
