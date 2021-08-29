package com.gdutelc.snp.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author kid
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result<T> {
    private int code;
    private String msg;
    private T data;


}
