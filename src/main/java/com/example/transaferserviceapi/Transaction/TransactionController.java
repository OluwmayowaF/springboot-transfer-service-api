package com.example.transaferserviceapi.Transaction;

import java.util.*;

import com.example.transaferserviceapi.Balance.BalanceService;
import com.example.transaferserviceapi.Balance.Balance;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.web.bind.annotation.*;


@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BalanceService balanceService;
     
    private Balance from_balance;

    private Balance to_balance;

    @GetMapping("/api/v1/transactions")
     public Iterable<Transaction> list() {
    return transactionService.getAll();
    }

    @PostMapping("/api/v1/transactions")
    public ResponseEntity<Object> addTransaction (@RequestParam String reference
      , @RequestParam Long amount, @RequestParam String from_account, @RequestParam String to_account ) {
    try{
        Long base_balance = 50000L; // Credit users with a base balance of 50,000 since module doesnt have a credit method


        if(reference == "" || amount < 0 || from_account == "" || to_account == ""){
            HashMap<String, String> response = new HashMap<String, String>();
            response.put("status_code", "400");
            response.put("message", "All Fields are required");
            return ResponseEntity.badRequest().body(response);
 
        }
        // Check the balance table for users balance using account_number
        from_balance = balanceService.getUserAccount(from_account);
        to_balance = balanceService.getUserAccount(to_account);
       
        // Create the Users Balance if the balance row is not found
        if(to_balance == null ){   
           
            to_balance = new Balance();
           
            to_balance.setAccountNumber(to_account);
           
            to_balance.setBalance(base_balance);
           
        }
         // Create the Users Balance if the balance row is not found
         if(from_balance == null ){   
            
           
            from_balance = new Balance();
           
            from_balance.setAccountNumber(from_account);
           
            from_balance.setBalance(base_balance);

        }
        else if (from_balance.getBalance() < amount){ // Ensure User has enough funds to make a transfer
            HashMap<String, String> response = new HashMap<String, String>();
            response.put("status_code", "400");
            response.put("message", "Insufficient Funds");
    
            return ResponseEntity.badRequest().body(response);
        }
        // Update From transaction 
            Transaction transaction_from = new Transaction();
          
            transaction_from.setReference("DEBIT"+reference);
          
            transaction_from.setAmount(-amount);
          
            transaction_from.setAccountNumber(from_account);

            transactionService.save(transaction_from);

            Transaction transaction_to = new Transaction();

            transaction_to.setReference("CREDIT"+reference);
          
            transaction_to.setAmount(amount);
          
            transaction_to.setAccountNumber(to_account);

            transactionService.save(transaction_to);
          
            from_balance.setBalance(from_balance.getBalance() - amount);

            to_balance.setBalance(to_balance.getBalance() + amount);
          
            balanceService.save(from_balance);
            balanceService.save(to_balance);

            HashMap<String, String> response = new HashMap<String, String>();
        
            response.put("status_code", "201");

            response.put("status", "success");
            
            response.put("message", "Transfer successful");


            return new ResponseEntity<>(response, HttpStatus.CREATED);
        
    }catch (DataIntegrityViolationException e){ // If an existing reference number is used to query the api do not allow creation

        HashMap<String, String> response = new HashMap<String, String>();
        
        response.put("status_code", "400");
        
        response.put("message", "Transaction already Exists");

        return ResponseEntity.badRequest().body(response);

    }  catch(Exception e){
        throw e;
        //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

  }

  @GetMapping("/api/v1/transactions/{id}")
  public ResponseEntity<Transaction> get(@PathVariable Integer id) {
      try {
          Transaction transaction = transactionService.get(id);
          return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
      } catch (NoSuchElementException e) {
          return new ResponseEntity<Transaction>(HttpStatus.NOT_FOUND);
      }      
  }

    
}
