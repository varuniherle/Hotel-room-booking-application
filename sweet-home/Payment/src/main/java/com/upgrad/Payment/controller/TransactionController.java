package com.upgrad.Payment.controller;

import com.upgrad.Payment.dto.TransactionDTO;
import com.upgrad.Payment.entities.TransactionDetailsEntity;
import com.upgrad.Payment.services.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/payment")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/transaction")
    public ResponseEntity acceptPayment(@RequestBody TransactionDTO transactionDTO){

        TransactionDetailsEntity transactionDetailsEntity = modelMapper.map(transactionDTO, TransactionDetailsEntity.class);
        TransactionDetailsEntity savedTransactionDetail = transactionService.acceptBooking(transactionDetailsEntity);
        String paymentMode = transactionDTO.getPaymentMode();
        switch (paymentMode){
            case "UPI": System.out.println("Booking confirmed for user with "+ paymentMode + ".The number is: " + transactionDTO.getUpiId());
                        break;

            case  "CARD":System.out.println("Booking confirmed for user with "+ paymentMode + ".The number is: " + transactionDTO.getCardNumber());
                break;
        }

        System.out.println("Booking details - \n"+ savedTransactionDetail.toString());

        return new ResponseEntity(savedTransactionDetail.getTransactionId(),HttpStatus.CREATED);
    }

    @GetMapping(value = "/transactions")
    public ResponseEntity getAllBookings(){
        List<TransactionDetailsEntity> allTransactions = transactionService.getAllTransaction();

        return new ResponseEntity(allTransactions,HttpStatus.OK);

    }

    @GetMapping(value = "/transaction/{transactionId}")
    public ResponseEntity getBookings(@PathVariable int transactionId){
        TransactionDetailsEntity transaction = transactionService.getTransaction(transactionId);

        if(transaction == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(transaction,HttpStatus.OK);

    }

}
