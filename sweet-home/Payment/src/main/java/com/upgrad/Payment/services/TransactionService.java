package com.upgrad.Payment.services;

import com.upgrad.Payment.entities.TransactionDetailsEntity;

import java.util.List;

public interface TransactionService {

    public TransactionDetailsEntity acceptBooking(TransactionDetailsEntity transactionDetailsEntity);

    public List<TransactionDetailsEntity> getAllTransaction();

    public TransactionDetailsEntity getTransaction(int transactionId);
}
