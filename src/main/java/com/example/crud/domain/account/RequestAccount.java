package com.example.crud.domain.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestAccount (
        String id,
        @NotBlank
        String name,
        @NotNull
        Integer number_account,
        Integer agency
) {
}
