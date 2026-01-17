package com.natixis.bank_transaction_api.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "amount",
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal amount;

    @Column(
            name = "transaction_date",
            nullable = false
    )
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private TaxType taxType;

    @Column(
            name = "tax_amount",
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal taxAmount;

    @Column(
            name = "result_amount",
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal resultAmount;

    protected TransactionEntity() {}

    public TransactionEntity(BigDecimal amount, LocalDate transactionDate, TaxType taxType, BigDecimal taxedAmount, BigDecimal resultAmount) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.taxType = taxType;
        this.taxAmount = taxedAmount;
        this.resultAmount = resultAmount;
    }
}
