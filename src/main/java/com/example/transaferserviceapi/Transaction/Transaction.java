package com.example.transaferserviceapi.Transaction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;

import javax.validation.constraints.*;


@Entity // This tells Hibernate to make a table out of this class
public class Transaction {
    private  Integer id;
    private  String reference;
    private  Long amount; 
    private  String transaction_type;
    private  String account_number;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId(){
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull(message = "reference is required")
    @Column(name = "reference", unique= true)
    public String getReference() {
        return reference; 
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @NotNull(message = "amount is required")
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }


    public String getTransactionType(){
        return transaction_type;
    }

    public void setTransactionType(String transaction_type) {
        this.transaction_type = transaction_type;
    }


    @NotNull(message = "account number is required")
    public String getAccountNumber(){
        return account_number;
    }

    public void setAccountNumber(String account_number) {
        this.account_number = account_number;
    }
   

    
}
