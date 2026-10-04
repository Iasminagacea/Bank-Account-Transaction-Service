package com.iasmina.bank_api.service;

import com.iasmina.bank_api.model.Account;
import com.iasmina.bank_api.model.Transaction;
import com.iasmina.bank_api.repository.AccountRepository;
import com.iasmina.bank_api.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account createAccount(Account account) {
        account.setBalance(BigDecimal.ZERO); 
        return accountRepository.save(account);
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Transaction processTransaction(Long accountId, BigDecimal amount, String type) {
        Account account = getAccount(accountId);

        if ("WITHDRAW".equalsIgnoreCase(type) && account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        if ("DEPOSIT".equalsIgnoreCase(type)) {
            account.setBalance(account.getBalance().add(amount));
        } else if ("WITHDRAW".equalsIgnoreCase(type)) {
            account.setBalance(account.getBalance().subtract(amount));
        }

        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(type.toUpperCase());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAccount(account);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}