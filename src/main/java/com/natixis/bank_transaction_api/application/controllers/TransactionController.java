package com.natixis.bank_transaction_api.application.controllers;

import com.natixis.bank_transaction_api.application.dtos.TransactionRequest;
import com.natixis.bank_transaction_api.application.dtos.TransactionResponse;
import com.natixis.bank_transaction_api.domain.transaction.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@Validated
@Tag(name = "Transaction", description = "Bank transaction operations")
public class TransactionController {

    private final TransactionService service;

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get transactions", description = "Returns all transactions of a costumer")
    public ResponseEntity<List<TransactionResponse>> getTransactions() {
        return ResponseEntity.of(Optional.ofNullable(service.getScheduledTransactions()));
    }

    @PostMapping
    @Operation(summary = "Post transactions", description = "Schedules a transaction")
    public ResponseEntity<TransactionResponse> scheduleTransaction(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = service.scheduleTransaction(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{transactionId}")
    @Operation(summary = "Put transaction", description = "Updates a transaction by transactionId")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @PathVariable UUID transactionId,
            @Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = service.updateTransaction(transactionId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{transactionId}")
    @Operation(summary = "Delete Transaction", description = "Deletes a transaction by its ID")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID transactionId) {
        service.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }
}
