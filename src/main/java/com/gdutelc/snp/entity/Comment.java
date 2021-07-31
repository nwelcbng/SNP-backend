package com.gdutelc.snp.entity;


import lombok.Data;

import java.util.Date;

/**
 * @author kid
 */
@Data
public class Comment {
    private Integer cid;
    private Integer aid;
    private Integer uid;
    private String  comment;
    private Date    time;
}
