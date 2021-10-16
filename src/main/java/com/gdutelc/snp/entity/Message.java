package com.gdutelc.snp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author kid
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private String name;
    private Integer position;
    private String session;
    private String openid;
}
