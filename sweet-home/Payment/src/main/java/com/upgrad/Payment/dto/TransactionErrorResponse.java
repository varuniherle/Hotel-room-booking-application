package com.upgrad.Payment.dto;

public class TransactionErrorResponse {

    private String message;
    private int statusCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public TransactionErrorResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
