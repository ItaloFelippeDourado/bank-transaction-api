package com.natixis.bank_transaction_api.application.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(
        @NotNull(message = "Amount não pode ser nulo")
        @DecimalMin(value = "1", message = "Amount deve ser maior que 0")
        BigDecimal amount,


        LocalDate transactionDate)
{}
