package com.gdutelc.snp.result;

/**
 * @author kid
 */
public class Return {
    public static <T> Result<T> success(){
       return new Result<>(Status.SUCCESS.getCode(),Status.SUCCESS.getMsg(),null);
    }
    public static <T> Result<T> success(T data){
        return new Result<>(Status.SUCCESS.getCode(),Status.SUCCESS.getMsg(),data);
    }
    public static <T> Result<T> error(Status status){
        return new Result<>(status.getCode(),status.getMsg(),null);
    }
//    public static <T> Result<T> error(int code,String msg){
//        return new Result<>(code,msg,null);
//    }
//    public static <T> Result<T> error(int code,String msg,T data){
//        return new Result<>(code,msg,data);
//    }
    public static <T> Result<T> error(Status status,T data){
        return new Result<>(status.getCode(),status.getMsg(),data);
    }
}
