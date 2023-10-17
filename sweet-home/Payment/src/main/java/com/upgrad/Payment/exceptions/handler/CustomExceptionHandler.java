package com.upgrad.Payment.exceptions.handler;

import com.upgrad.Payment.dto.TransactionErrorResponse;
import com.upgrad.Payment.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ErrorCode.class)
    public ResponseEntity<TransactionErrorResponse> handleRecordNotFoundException(ErrorCode recordNotFoundException ) {


        TransactionErrorResponse response = new TransactionErrorResponse(recordNotFoundException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

    }
}
