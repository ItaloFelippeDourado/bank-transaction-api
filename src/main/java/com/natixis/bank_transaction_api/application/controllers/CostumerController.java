package com.natixis.bank_transaction_api.application.controllers;

import com.natixis.bank_transaction_api.application.dtos.CostumerRequest;
import com.natixis.bank_transaction_api.domain.costumer.CostumerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/costumer")
@Validated
@Tag(name = "Costumer", description = "Costumer registry operations")
public class CostumerController {

    private final CostumerService costumerService;

    @Autowired
    public CostumerController(CostumerService costumerService) {
        this.costumerService = costumerService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register", description = "Create a costumer account")
    public ResponseEntity<String> register(@Valid @RequestBody CostumerRequest request) {
        costumerService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }
}
