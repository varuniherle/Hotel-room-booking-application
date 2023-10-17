package com.upgrad.Booking.controller;

import com.upgrad.Booking.dto.BookingDTO;
import com.upgrad.Booking.dto.IncomingBookingDTO;
import com.upgrad.Booking.entities.BookingInfoEntity;
import com.upgrad.Booking.entities.TransactionDetailsEntity;
import com.upgrad.Booking.exceptions.BookingErrorCode;
import com.upgrad.Booking.services.BookingService;
import org.apache.coyote.Response;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "/hotel")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/booking")
    public ResponseEntity acceptBookingDetails(@RequestBody BookingDTO bookingDTO){

        System.out.println(bookingDTO);
        BookingInfoEntity bookingInfoEntity = modelMapper.map(bookingDTO,BookingInfoEntity.class);
        System.out.println(bookingInfoEntity);
        BookingInfoEntity bookedInfo = bookingService.acceptBooking(bookingInfoEntity);

        return new ResponseEntity(bookedInfo, HttpStatus.CREATED);
    }

    @PostMapping(value = "/booking/{bookingId}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity acceptPaymentDetails(@PathVariable(name = "bookingId") int bookingId,
                                               @RequestBody IncomingBookingDTO incomingBookingDTO){

        String paymentMode = incomingBookingDTO.getPaymentMode();
        String upiId = incomingBookingDTO.getUpiId();
        String cardNumber = incomingBookingDTO.getCardNumber();

        int transactionID = bookingService.acceptPayment(bookingId, paymentMode, upiId, cardNumber);
        BookingInfoEntity completeBookingInfoEntity = bookingService.getBooking(bookingId);

        return new ResponseEntity(completeBookingInfoEntity, HttpStatus.CREATED);


    }

    @GetMapping(value = "/bookings")
    public ResponseEntity getAllBookings(){
        List<BookingInfoEntity> allBookings = bookingService.getAllBookings();

        return new ResponseEntity(allBookings,HttpStatus.OK);

    }

    @GetMapping(value = "/booking/{id}")
    public ResponseEntity getBooking(@PathVariable int id){

        BookingInfoEntity booking = bookingService.getBooking(id);
        return new ResponseEntity(booking,HttpStatus.OK);

    }

    @PutMapping(value = "/booking/{bookingId}/{txnId}")
    public ResponseEntity updateBooking(@PathVariable (name = "bookingId") int bookingId, @PathVariable (name = "txnId") int txnId){

        BookingInfoEntity bookingInfoEntity = bookingService.getBooking(bookingId);
        BookingInfoEntity updatedBookingInfoEntity = bookingService.updateBooking(bookingInfoEntity, txnId);

        return new ResponseEntity(updatedBookingInfoEntity, HttpStatus.OK);
    }


}
