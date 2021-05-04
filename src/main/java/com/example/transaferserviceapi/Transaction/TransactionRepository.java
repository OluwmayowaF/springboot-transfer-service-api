package com.example.transaferserviceapi.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transaferserviceapi.Transaction.Transaction;

public interface TransactionRepository extends  JpaRepository<Transaction, Integer> {
    Transaction findByReference(String reference);

}
