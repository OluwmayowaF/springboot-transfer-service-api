package com.example.transaferserviceapi.Balance;

import org.springframework.data.repository.CrudRepository;

import com.example.transaferserviceapi.Balance.Balance;

public interface BalanceRepository extends CrudRepository<Balance, Integer> {
    Balance findByAccountNumber(String account_number);
}
