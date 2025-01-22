package com.ademarporto.ba.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record AccountRequest(
    UUID requestId,
    String name,
    LocalDate dateOfBirth,
    String idDocument,
    Address address,
    AccountType accountType,
    BigDecimal startingBalance,
    BigDecimal monthlySalary,
    Boolean interestedInOtherProducts,
    String email,
    AccountStatus accountStatus) {}
