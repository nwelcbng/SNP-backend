package com.gdutelc.snp.exception;

import com.gdutelc.snp.result.Status;

/**
 * @author kid
 */
public class AdminServiceException extends RuntimeException{

    private final Status status;

    public  AdminServiceException(Status status){

        super(status.getMsg());
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
