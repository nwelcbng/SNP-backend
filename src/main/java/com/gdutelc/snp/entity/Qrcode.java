package com.gdutelc.snp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 10501
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qrcode implements Serializable {
    private int uid;
    private int code;
}
