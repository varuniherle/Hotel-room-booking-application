package com.upgrad.Booking.exceptions;

public class BookingErrorCode extends RuntimeException{
    public BookingErrorCode(String message){
        super(message);
    }
}
