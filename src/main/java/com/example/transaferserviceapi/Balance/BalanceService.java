package com.example.transaferserviceapi.Balance;

import org.springframework.transaction.annotation.Transactional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BalanceService {
    @Autowired
    private BalanceRepository balanceRepository;

    public Iterable<Balance> getAll() {
        return balanceRepository.findAll();
    }
     
    public void save(Balance balance) {
        balanceRepository.save(balance);
    }
     
    public Balance getUserAccount(String account_number) {
        return balanceRepository.findByAccountNumber(account_number);
    }

     
    public void delete(Integer id) {
        balanceRepository.deleteById(id);
    }

}