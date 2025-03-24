package com.example.crud.controllers;

import com.example.crud.domain.account.Account;
import com.example.crud.domain.account.RequestAccount;
import com.example.crud.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/account")
public class AccountController{
    @Autowired
    private AccountRepository repository;
    @GetMapping
    public ResponseEntity getAllAccounts(){
        var allAccounts = repository.findAllByActiveTrue();
        return ResponseEntity.ok(allAccounts);
    }

    @PostMapping
    public ResponseEntity registerAccount(@RequestBody @Valid RequestAccount data){
        Account newAccount = new Account(data);
        repository.save(newAccount);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateAccount(@RequestBody @Valid RequestAccount data){
        Optional<Account> optionalAccount = repository.findById(data.id());
        if (optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            account.setName(data.name());
            account.setNumber_account(data.number_account());
            account.setAgency(data.agency());
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteAccount(@PathVariable String id){
        Optional<Account> optionalAccount = repository.findById(id);
        if (optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            account.setActive(false);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}