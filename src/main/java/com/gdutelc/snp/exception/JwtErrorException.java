package com.gdutelc.snp.exception;

/**
 * @author kid
 */
public class JwtErrorException extends RuntimeException{
    public JwtErrorException(){

    }
    public JwtErrorException(String message){
        super(message);
    }
}
