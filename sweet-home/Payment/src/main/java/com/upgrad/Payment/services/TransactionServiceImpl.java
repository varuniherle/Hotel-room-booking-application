package com.upgrad.Payment.services;

import com.upgrad.Payment.dao.TransactionDao;
import com.upgrad.Payment.entities.BookingInfoEntity;
import com.upgrad.Payment.entities.TransactionDetailsEntity;
import com.upgrad.Payment.exceptions.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public TransactionDetailsEntity acceptBooking(TransactionDetailsEntity transactionDetailsEntity) {

        String bookingURL = "http://localhost:8080/hotel/booking/"+transactionDetailsEntity.getBookingId();
        TransactionDetailsEntity savedTransactionDetailEntity = null;

        ResponseEntity<BookingInfoEntity> getBookingDetailResponse = restTemplate.getForEntity(bookingURL, BookingInfoEntity.class);
        if(!(transactionDetailsEntity.getPaymentMode().equals("UPI") || transactionDetailsEntity.getPaymentMode().equals("CARD")) ){
            throw new ErrorCode("Invalid payment mode");
        }

        if(getBookingDetailResponse.hasBody()) {
            savedTransactionDetailEntity = transactionDao.save(transactionDetailsEntity);
            int txnId = savedTransactionDetailEntity.getTransactionId();

            String bookingUpdateURL = "http://localhost:8080/hotel/booking/" + transactionDetailsEntity.getBookingId() + "/" + txnId;
            restTemplate.put(bookingUpdateURL, null);

        }


        return savedTransactionDetailEntity;

    }

    @Override
    public List<TransactionDetailsEntity> getAllTransaction() {
        List<TransactionDetailsEntity> getAllTransaction = transactionDao.findAll();

        return getAllTransaction;
    }

    @Override
    public TransactionDetailsEntity getTransaction(int transactionId) {
        return transactionDao.findById(transactionId).get();
    }
}
