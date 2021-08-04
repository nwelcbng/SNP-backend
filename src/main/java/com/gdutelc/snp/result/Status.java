package com.gdutelc.snp.result;


import com.sun.net.httpserver.Authenticator;

/**
 * @author kid
 */


public enum Status {
    JWTMISSERROR(-404,"jwt为空"),
    JWTERROR(-405,"jwt错误"),
    SUCCESS(1,"成功");
    private int code;
    private String msg;

    Status(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Status{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
