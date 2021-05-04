package com.example.transaferserviceapi.Balance;

import java.util.*;
 
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
 
import org.springframework.web.bind.annotation.*;

@RestController
public class BalanceController {
    @Autowired
    private BalanceService balanceService;

    @GetMapping("/api/v1/balances")
     public Iterable<Balance> list() {
    return balanceService.getAll();
    }

    @GetMapping("/api/v1/balances/{account_number}")
    public ResponseEntity<Balance> get(@PathVariable String account_number) {
      try {
          Balance balance = balanceService.getUserAccount(account_number);
          return new ResponseEntity<Balance>(balance, HttpStatus.OK);
      } catch (NoSuchElementException e) {
          return new ResponseEntity<Balance>(HttpStatus.NOT_FOUND);
      }      
  }
}