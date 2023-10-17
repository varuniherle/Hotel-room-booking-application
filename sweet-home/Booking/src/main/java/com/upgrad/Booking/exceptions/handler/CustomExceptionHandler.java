package com.upgrad.Booking.exceptions.handler;

import com.upgrad.Booking.dto.BookingErrorResponse;
import com.upgrad.Booking.exceptions.BookingErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BookingErrorCode.class)
    public ResponseEntity<BookingErrorResponse> handleRecordNotFoundException(BookingErrorCode recordNotFoundException ) {


        BookingErrorResponse response = new BookingErrorResponse(recordNotFoundException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

    }
}
