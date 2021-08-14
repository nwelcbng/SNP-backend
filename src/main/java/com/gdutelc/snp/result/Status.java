package com.gdutelc.snp.result;


import com.sun.net.httpserver.Authenticator;

/**
 * @author kid
 */


public enum Status {
    PASSWORDERROR(-400,"密码错误"),
    JWTERROR(-401,"jwt错误"),
    JWTUPDATEERROR(-402,"jwt更新失败"),
    WEBLOGINFALL(-403,"网络端登录失败"),
    JWTMISSERROR(-404,"jwt为空"),
    GETQRCODEERROR(-405,"获取二维码失败"),
    REQUESTERROREXCEPTION(-406, "请求方法不支持"),
    PARAMETERERROREXCEPTION(-407, "参数错误"),
    GETOPENIDERROR(-501,"获取openid错误"),
    GETFORMERROR(-502,"获取报名表单错误"),
    SETSTATUSERROR(-503,"设置状态失败"),
    JWTCREATEERROR(-504,"jwt生成错误"),
    CHECKQRCODEERROR(-505,"扫描二维码登录失败"),
    POSTAPPSIGNERROR(-506,"获取小程序端报名信息失败"),
    POSTWEBSIGNERROR(-507,"获取web端报名信息失败"),
    POSTSIGNERROR(-508,"获取报名信息失败"),
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
