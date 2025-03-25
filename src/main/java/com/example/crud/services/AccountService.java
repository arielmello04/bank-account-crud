package com.example.crud.services;

import com.example.crud.domain.account.Account;
import com.example.crud.domain.account.CreateAccountDTO;
import com.example.crud.domain.account.UpdateAccountDTO;
import com.example.crud.repositories.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> getAllActiveAccounts() {
        return repository.findAllByActiveTrue();
    }

    public Account createAccount(CreateAccountDTO data) {
        Account newAccount = new Account(data);
        return repository.save(newAccount);
    }

    @Transactional
    public Account updateAccount(UpdateAccountDTO data) {
        Account account = repository.findById(data.id())
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

        account.setName(data.name());
        account.setNumberAccount(data.number_account());
        account.setAgency(data.agency());

        return repository.save(account);
    }

    @Transactional
    public void deleteAccount(String id) {
        Account account = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
        account.setActive(false);
        repository.save(account);
    }
}