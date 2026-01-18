package com.natixis.bank_transaction_api.application.dtos;

import com.natixis.bank_transaction_api.domain.transaction.TaxType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        BigDecimal amount,
        LocalDate transactionDate,
        TaxType taxType,
        BigDecimal taxApplied,
        BigDecimal resultAmount
)
{}
