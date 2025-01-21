package com.ademarporto.ba.repository.entity;


import com.ademarporto.ba.model.AccountStatus;
import com.ademarporto.ba.model.AccountType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "account_requests")
public class AccountRequestEntity {
    @Id
    @UuidGenerator
    private UUID requestId;

    private String name;

    private LocalDate dateOfBirth;

    private String idDocument;
    @Embedded
    private AddressEntity address;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private BigDecimal startingBalance;

    private BigDecimal monthlySalary;

    private Boolean interestedInOtherProducts;

    private String email;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
}
