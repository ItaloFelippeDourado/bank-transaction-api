package com.natixis.bank_transaction_api.application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Bank transaction operations")
public class TransactionController {

    @GetMapping
    @Operation(summary = "Get transactions", description = "Returns 'transactions'")
    public String getTransactions() {
        return "transactions";
    }
}
