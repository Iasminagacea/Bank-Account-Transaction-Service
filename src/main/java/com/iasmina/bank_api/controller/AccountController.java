package com.iasmina.bank_api.controller;

import com.iasmina.bank_api.model.Account;
import com.iasmina.bank_api.model.Transaction;
import com.iasmina.bank_api.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @PostMapping("/{id}/transactions")
    public ResponseEntity<Transaction> makeTransaction(@PathVariable Long id, @RequestBody Map<String, String> request) {
        BigDecimal amount = new BigDecimal(request.get("amount"));
        String type = request.get("type");
        return ResponseEntity.ok(accountService.processTransaction(id, amount, type));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getTransactions(id));
    }
}