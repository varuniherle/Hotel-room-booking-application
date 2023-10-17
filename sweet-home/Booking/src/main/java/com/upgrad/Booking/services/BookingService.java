package com.upgrad.Booking.services;

import com.upgrad.Booking.dto.IncomingBookingDTO;
import com.upgrad.Booking.dto.TransactionDTO;
import com.upgrad.Booking.entities.BookingInfoEntity;
import com.upgrad.Booking.entities.TransactionDetailsEntity;

import java.awt.print.Book;
import java.util.List;

public interface BookingService {

    public BookingInfoEntity acceptBooking(BookingInfoEntity bookingInfoEntity);

    public int acceptPayment(int bookingId, String paymentMode, String upiId, String cardNumber);

    public boolean checkBooking(int bookingID);

    public BookingInfoEntity getBooking(int bookingID);

    public List<BookingInfoEntity> getAllBookings();

    public BookingInfoEntity updateBooking(BookingInfoEntity bookingInfoEntity, int txnId);



}
