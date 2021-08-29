package com.gdutelc.snp.controller;

import com.gdutelc.snp.exception.*;
import com.gdutelc.snp.result.Result;
import com.gdutelc.snp.result.Return;
import com.gdutelc.snp.result.Status;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @author kid
 */
@ControllerAdvice
public class GlobalExceptionController extends BaseController{

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Status> getMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage(), e);
        return Return.error(Status.PARAMETERERROREXCEPTION);
    }
    @ExceptionHandler(AdminServiceException.class)
    public Result<Status> adminServiceException(AdminServiceException e){
        log.error(e.getStatus().getMsg());
        return Return.error(e.getStatus());
    }

    @ExceptionHandler(JwtErrorException.class)
    public Result<Status> jwtErrorException(JwtErrorException e){
        log.error(e.getStatus().getMsg());
        return Return.error(e.getStatus());
    }
    @ExceptionHandler(UserServiceException.class)
    public Result<Status> userServiceException(UserServiceException e){
        log.error(e.getStatus().getMsg());
        return Return.error(e.getStatus());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result<Status> handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return Return.error(Status.REQUESTERROREXCEPTION);
    }
//    @ExceptionHandler()


}
