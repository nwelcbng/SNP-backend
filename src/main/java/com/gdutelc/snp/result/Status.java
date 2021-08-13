package com.gdutelc.snp.result;


import com.sun.net.httpserver.Authenticator;

/**
 * @author kid
 */


public enum Status {
    JWTMISSERROR(-404,"jwt为空"),
    JWTERROR(-401,"jwt错误"),
    JWTCREATEERROR(-505,"jwt生成错误"),
    GETOPENIDERROR(-501,"获取openid错误"),
    GETFORMERROR(-502,"获取报名表单错误"),
    SETSTATUSERROR(-503,"设置状态失败"),
    GETQRCODEERROR(-401,"获取二维码失败"),
    CHECKQRCODEERROR(-505,"扫描二维码登录失败"),
    WEBLOGINFALL(-403,"网络端登录失败"),
    JWTUPDATEERROR(-402,"jwt更新失败"),
    POSTAPPSIGNERROR(-510,"获取小程序端报名信息失败"),
    POSTWEBSIGNERROR(-511,"获取web端报名信息失败"),
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
