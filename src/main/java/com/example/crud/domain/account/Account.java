package com.example.crud.domain.account;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "account")
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class Account {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private Integer number_account;

    private Integer agency;

    private Boolean active;

    public Account(RequestAccount requestAccount){
        this.name = requestAccount.name();
        this.number_account = requestAccount.number_account();
        this.agency = requestAccount.agency();
        this.active = true;
    }
}
