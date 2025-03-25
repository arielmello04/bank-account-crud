package com.example.crud.domain.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateAccountDTO(
        @NotBlank(message = "ID é obrigatório")
        String id,

        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotNull(message = "Número da conta é obrigatório")
        @Positive(message = "Número deve ser positivo")
        Integer number_account,

        @NotNull(message = "Agência é obrigatória")
        @Positive(message = "Agência deve ser positiva")
        Integer agency
) {}