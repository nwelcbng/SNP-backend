package com.gdutelc.snp.result;


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
    PHONECODEERROR(-408,"手机验证码发送失败"),
    JWTCHANGE(-409,"用户jwt不为空但openid已修改或者jwt超过过期时间"),
    GETOPENIDERROR(-501,"获取openid错误"),
    GETFORMERROR(-502,"获取报名表单错误"),
    SETSTATUSERROR(-503,"设置状态失败"),
    JWTCREATEERROR(-504,"jwt生成错误"),
    CHECKQRCODEERROR(-505,"扫描二维码登录失败"),
    POSTAPPSIGNERROR(-506,"上传小程序端报名信息失败"),
    POSTWEBSIGNERROR(-507,"上传web端报名信息失败"),
    POSTSIGNERROR(-508,"获取报名信息失败"),
    GETPHONEERROR(-509,"数据库查找手机号失败"),
    GETRESOURCEERROR(-510,"返回内容为空"),
    CHECKPHONEERROR(-511,"验证手机号码失败"),
    GETPASSWORDERROR(-512,"数据库获取密码失败"),
    POSTPHONEERROR(-513,"获取手机号码失败"),
    CONFIRMFAIL(-514,"发送确认面试结果信息失败"),
    REGISTERERROR(-600,"该用户的session已过期"),
    USERINSERTERROR(-601,"无法往数据库存入user信息"),
    QRCODECHECKERROR(-602,"二维码检验失败"),
    QRUUIDERROR(-603,"二维码uuid有误"),
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


    @Override
    public String toString() {
        return "Status{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
