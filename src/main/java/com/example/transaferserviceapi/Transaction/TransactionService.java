package com.example.transaferserviceapi.Transaction;


 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
     
    public Iterable<Transaction> getAll() {
        return transactionRepository.findAll();
    }
     
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }
     
    public Transaction get(Integer id) {
        return transactionRepository.findById(id).get();
    }

    public Transaction getTranWithReference(String reference){
        return transactionRepository.findByReference(reference);
    }
     
    public void delete(Integer id) {
        transactionRepository.deleteById(id);
    }
}
