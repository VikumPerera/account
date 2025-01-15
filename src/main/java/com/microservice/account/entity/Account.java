package com.microservice.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Account extends BaseEntity{

    @Id
    private Long accountNumber;

    private Long customerId;

    private String accountType;

    private String branchAddress;

    private Boolean communicationStatus;

}
