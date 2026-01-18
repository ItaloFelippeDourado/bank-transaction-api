package com.natixis.bank_transaction_api.application.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        BigDecimal resultAmount,
        BigDecimal taxAmount
)
{}
