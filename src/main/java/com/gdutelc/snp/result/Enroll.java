package com.gdutelc.snp.result;

/**
 * @author kid
 */

public enum Enroll {

    NOPUSHSIGN(100,"未提交报名表"),
    HAVESIGN(101,"已提交报名表"),
    CHECKING(110,"正在审核报名表"),
    HAVECHECKING(111,"已经通过审核"),
    FIRSTNOCHECK(200,"一面待确认"),
    FIRSTCHECKED(201,"一面确认参加"),
    FIRSTGIVEUP(202,"一面放弃"),
    FIRSTNOSIGN(210,"一面待签到"),
    FIRSTSGINED(211,"一面已签到"),
    FIRSTNOCALL(220,"等待叫号"),
    FIRSTCALLING(230,"面试中"),
    FIRSTOVER(240,"面试结束"),
    PENNOCALL(300,"笔试通知待确认"),
    PENCHECK(301,"笔试签到确认参加"),
    PENGIVEUP(302,"笔试通知放弃"),
    PENNOCHECK(310,"笔试待签到"),
    PENCHECKED(311,"笔试已签到"),
    PENNOPUSH(320,"笔试未交卷"),
    PENPUSH(321,"笔试已交卷"),
    SECWAIT(400,"二轮等待叫号"),
    SECWAITSEC(410,"等待第二轮面试"),
    FINALL(500,"未知结果"),
    FINALLCHECK(501,"已录用"),
    FINALLNOCHECK(502,"未录用");
    private int code;
    private String msg;


    Enroll(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
