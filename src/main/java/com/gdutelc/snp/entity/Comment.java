package com.gdutelc.snp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author kid
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer cid;
    private Integer aid;
    private Integer uid;
    private String  comment;
    private Date    time;
}
