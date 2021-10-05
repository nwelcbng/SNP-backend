package com.gdutelc.snp.exception;

import com.gdutelc.snp.result.Status;

/**
 * @author kid
 */
public class AdminServiceException extends RuntimeException{

    private final Status status;

    private final String message;

    public  AdminServiceException(Status status, String message){
        this.status = status;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
