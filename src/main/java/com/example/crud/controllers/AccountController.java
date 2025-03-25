package com.example.crud.controllers;

import com.example.crud.domain.account.Account;
import com.example.crud.domain.account.CreateAccountDTO;
import com.example.crud.domain.account.UpdateAccountDTO;
import com.example.crud.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = service.getAllActiveAccounts();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    public ResponseEntity<Account> registerAccount(
            @RequestBody @Valid CreateAccountDTO data,
            UriComponentsBuilder uriBuilder
    ) {
        Account newAccount = service.createAccount(data);
        URI location = uriBuilder
                .path("/account/{id}")
                .buildAndExpand(newAccount.getId())
                .toUri();
        return ResponseEntity.created(location).body(newAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(
            @PathVariable String id,
            @RequestBody @Valid UpdateAccountDTO data
    ) {
        if (!id.equals(data.id())) {
            return ResponseEntity.badRequest().build();
        }

        Account updatedAccount = service.updateAccount(data);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String id) {
        service.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}