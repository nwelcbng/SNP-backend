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
        log.info(e.getMessage(), e);
        return Return.error(Status.PARAMETERERROREXCEPTION);
    }
    @ExceptionHandler(AdminErrorException.class)
    public Result<Status> getAdminErrorException(AdminErrorException e) {
        log.info(e.getMessage(), e);
        return Return.error(Status.PASSWORDERROR);
    }
    @ExceptionHandler(JwtErrorException.class)
    public Result<Status> getJwtErrorException(JwtErrorException e) {
        log.info(e.getMessage(), e);
        return Return.error(Status.JWTERROR);
    }
    @ExceptionHandler(GetFormErrorException.class)
    public Result<Status> getFormErrorException(GetFormErrorException e) {
        log.info(e.getMessage(), e);
        return Return.error(Status.GETFORMERROR);
    }
    @ExceptionHandler(QrCodeErrorException.class)
    public Result<Status> qrCodeErrorException(QrCodeErrorException e) {
        log.info(e.getMessage(), e);
        return Return.error(Status.CHECKQRCODEERROR);
    }

    @ExceptionHandler(RegisterErrorException.class)
    public Result<Status> registerErrorException(RegisterErrorException e) {
        log.info(e.getMessage(), e);
        return Return.error(Status.GETOPENIDERROR);
    }

    @ExceptionHandler(SetStatusErrorException.class)
    public Result<Status> setStatusErrorException(SetStatusErrorException e) {
        log.info(e.getMessage(), e);
        return Return.error(Status.SETSTATUSERROR);
    }
    @ExceptionHandler(SignPushErrorException.class)
    public Result<Status> signPushErrorException(SignPushErrorException e) {
        log.info(e.getMessage(), e);
        return Return.error(Status.POSTSIGNERROR);
    }
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result<Status> handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return Return.error(Status.REQUESTERROREXCEPTION);
    }

}
