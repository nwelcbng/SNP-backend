package com.gdutelc.snp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author kid
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sign {
    private Integer sid;
    private Integer uid;
    private String name;
    private Integer grade;
    private Integer college;
    private String major;
    private String userclass;
    private String description;
    private Integer dno;
    private Integer secdno;
    private Boolean gender;
    private String sno;
    private String qq;
    private String domitory;
    private String know;
    private String party;


}
