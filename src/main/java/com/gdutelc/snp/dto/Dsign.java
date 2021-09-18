package com.gdutelc.snp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dsign implements Serializable {
    private String name;
    private Integer grade;
    private Integer college;
    private String major;
    private String userclass;
    private String dsp;
    private Integer dno;
    private Integer secdno;
    private Boolean gender;
    private String sno;
    private String qq;
    private String domitory;
    private String know;
    private String party;



}
