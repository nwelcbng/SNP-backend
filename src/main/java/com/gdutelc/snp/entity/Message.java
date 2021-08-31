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
public class Message {
    private String name;
    private Integer position;
    private String session;
    private String openid;
}
