package com.natixis.bank_transaction_api.application.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @GetMapping
    public String getTransactions() {
        return "transactions";
    }
}
