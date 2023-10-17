package com.upgrad.Booking.services;

import com.upgrad.Booking.dao.BookingDao;
import com.upgrad.Booking.dto.IncomingBookingDTO;
import com.upgrad.Booking.entities.BookingInfoEntity;
import com.upgrad.Booking.exceptions.BookingErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private RestTemplate restTemplate;
    public static ArrayList<String> getRandomNumbers(int count){
        Random rand = new Random();
        int upperBound = 100;
        ArrayList<String>numberList = new ArrayList<String>();

        for (int i=0; i<count; i++){
            numberList.add(String.valueOf(rand.nextInt(upperBound)));
        }


        return numberList;
    }
    @Override
    public BookingInfoEntity acceptBooking(BookingInfoEntity bookingInfoEntity) {

        int numOfRooms = bookingInfoEntity.getNumOfRooms();
        bookingInfoEntity.setRoomNumbers(getRandomNumbers(numOfRooms));

        bookingInfoEntity.setRoomPrice((int) 1000*numOfRooms);

        bookingInfoEntity.setBookedOn(OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        return bookingDao.save(bookingInfoEntity);

    }

    @Override
    public boolean checkBooking(int bookingID) {
        Optional<BookingInfoEntity> isBooking = bookingDao.findById(bookingID);

        if(isBooking.isEmpty())
            return false;
        else
            return true;
    }

    @Override
    public BookingInfoEntity getBooking(int bookingID) {

        Optional<BookingInfoEntity> bookingInfoEntity = bookingDao.findById(bookingID);

        if(bookingInfoEntity.isEmpty()){
            System.out.println("-------------------------Entered Here------------------");
            throw new BookingErrorCode("Invalid Booking Id");
        }

        return bookingInfoEntity.get();

    }

    @Override
    public List<BookingInfoEntity> getAllBookings() {
        List<BookingInfoEntity> allBookings = bookingDao.findAll();

        return allBookings;
    }

    @Override
    public BookingInfoEntity updateBooking(BookingInfoEntity bookingInfoEntity, int txnId) {
        bookingInfoEntity.setTransactionId(txnId);
        return bookingDao.save(bookingInfoEntity);
    }

    public int acceptPayment(int bookingId, String paymentMode, String upiId, String cardNumber){


        IncomingBookingDTO incomingBookingDTO = new IncomingBookingDTO();
        incomingBookingDTO.setBookingId(bookingId);
        incomingBookingDTO.setPaymentMode(paymentMode);
        incomingBookingDTO.setCardNumber(cardNumber);
        incomingBookingDTO.setUpiId(upiId);

        String paymentURL = "http://localhost:8081/payment/transaction";

        int receivedTransactionID =  restTemplate.postForObject(paymentURL, incomingBookingDTO,Integer.class);
        BookingInfoEntity updateBookingInfoEntity = bookingDao.findById(bookingId).get();
        updateBookingInfoEntity.setTransactionId(receivedTransactionID);
        bookingDao.save(updateBookingInfoEntity);


        return receivedTransactionID;




    }


}
