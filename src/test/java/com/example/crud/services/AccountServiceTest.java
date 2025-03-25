package com.example.crud.services;

import com.example.crud.domain.account.Account;
import com.example.crud.domain.account.CreateAccountDTO;
import com.example.crud.domain.account.UpdateAccountDTO;
import com.example.crud.repositories.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountService service;

    @Test
    void createAccount_WithValidData_ReturnsAccount() {
        // Preparação
        CreateAccountDTO dto = new CreateAccountDTO("Maria", 12345, 1);
        Account newAccount = new Account(dto);
        newAccount.setId("uuid-123");

        when(repository.save(any(Account.class))).thenReturn(newAccount);

        // Execução
        Account result = service.createAccount(dto);

        // Verificação
        assertNotNull(result);
        assertEquals("Maria", result.getName());
        assertEquals(12345, result.getNumberAccount());
        assertTrue(result.getActive());
        verify(repository, times(1)).save(any(Account.class));
    }

    @Test
    void getAllActiveAccounts_WhenExists_ReturnsList() {
        // Preparação
        Account account = new Account(new CreateAccountDTO("João", 54321, 2));
        account.setId("uuid-456");

        when(repository.findAllByActiveTrue()).thenReturn(List.of(account));

        // Execução
        List<Account> result = service.getAllActiveAccounts();

        // Verificação
        assertEquals(1, result.size());
        assertEquals("João", result.get(0).getName());
        verify(repository, times(1)).findAllByActiveTrue();
    }

    @Test
    void getAllActiveAccounts_WhenEmpty_ReturnsEmptyList() {
        when(repository.findAllByActiveTrue()).thenReturn(Collections.emptyList());

        List<Account> result = service.getAllActiveAccounts();

        assertTrue(result.isEmpty());
    }

    @Test
    void updateAccount_WithValidData_ReturnsUpdatedAccount() {
        // Preparação
        String id = "uuid-789";
        UpdateAccountDTO dto = new UpdateAccountDTO(id, "Maria Atualizada", 99999, 3);
        Account existingAccount = new Account(new CreateAccountDTO("Maria", 12345, 1));
        existingAccount.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(existingAccount));
        when(repository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Execução
        Account result = service.updateAccount(dto);

        // Verificação
        assertEquals("Maria Atualizada", result.getName());
        assertEquals(99999, result.getNumberAccount());
        assertEquals(3, result.getAgency());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(existingAccount);
    }

    @Test
    void updateAccount_WhenNotFound_ThrowsException() {
        UpdateAccountDTO dto = new UpdateAccountDTO("invalid-id", "Nome", 123, 1);

        when(repository.findById("invalid-id")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.updateAccount(dto));
    }

    @Test
    void deleteAccount_WithValidId_DeactivatesAccount() {
        // Preparação
        String id = "uuid-000";
        Account account = new Account(new CreateAccountDTO("José", 11111, 4));
        account.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(account));

        // Execução
        service.deleteAccount(id);

        // Verificação
        assertFalse(account.getActive());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(account);
    }

    @Test
    void deleteAccount_WhenInvalidId_ThrowsException() {
        when(repository.findById("invalid-id")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.deleteAccount("invalid-id"));
    }

}