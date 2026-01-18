package com.natixis.bank_transaction_api.application.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(
        @NotNull(message = "Amount can not be null")
        @DecimalMin(value = "1", message = "Amount must be greater than 0")
        BigDecimal amount,

        @NotNull(message = "transactionDate cannot be null")
        @FutureOrPresent(message = "Transaction date must be today or in the future")
        LocalDate transactionDate)
{}
