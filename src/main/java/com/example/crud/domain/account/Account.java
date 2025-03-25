package com.example.crud.domain.account;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "account",
        uniqueConstraints = @UniqueConstraint(
                name = "account_number_agency_unique",
                columnNames = {"number_account", "agency"}
        )
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Column(name = "number_account")
    private Integer numberAccount;

    private Integer agency;

    private Boolean active;

    public Account(CreateAccountDTO requestAccount) {
        this.name = requestAccount.name();
        this.numberAccount = requestAccount.number_account();
        this.agency = requestAccount.agency();
        this.active = true;
    }
}
