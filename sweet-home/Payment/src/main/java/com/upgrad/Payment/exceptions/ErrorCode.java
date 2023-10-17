package com.upgrad.Payment.exceptions;

public class ErrorCode extends RuntimeException{
    public ErrorCode(String message){
        super(message);
    }
}
