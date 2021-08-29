package com.gdutelc.snp.exception;

import com.gdutelc.snp.result.Status;

/**
 * @author kid
 */
public class UserServiceException extends RuntimeException{
    private final Status status;


    public UserServiceException(Status status){
        super(status.getMsg());
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
