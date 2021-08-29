package com.gdutelc.snp.exception;

import com.gdutelc.snp.result.Status;

/**
 * @author kid
 */
public class JwtErrorException extends RuntimeException{
    private final Status status;


    public JwtErrorException(Status status){
        super((status.getMsg()));
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
