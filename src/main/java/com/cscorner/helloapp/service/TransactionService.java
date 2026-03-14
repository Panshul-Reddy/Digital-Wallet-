package com.cscorner.helloapp.service;

import com.cscorner.helloapp.model.Transaction;
import com.cscorner.helloapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public String saveTransaction(Transaction transaction) {
        if (transaction.getAmount() <= 0) {
            return "Amount must be positive!";
        }

        if (transaction.getTransactionType().equals("Debit")) {
            double balance = repository.findByWalletId(transaction.getWalletId())
                .stream()
                .mapToDouble(t -> t.getTransactionType().equals("Credit") ? t.getAmount() : -t.getAmount())
                .sum();

            if (transaction.getAmount() > balance) {
                return "Insufficient balance!";
            }
        }

        repository.save(transaction);
        return "success";
    }
}