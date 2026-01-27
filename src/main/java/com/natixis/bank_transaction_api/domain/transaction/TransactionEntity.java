package com.natixis.bank_transaction_api.domain.transaction;

import com.natixis.bank_transaction_api.domain.costumer.CostumerEntity;
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
            name = "tax_applied",
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal taxApplied;

    @Column(
            name = "result_amount",
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal resultAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "costumer_id", nullable = false)
    private CostumerEntity costumer;

    protected TransactionEntity() {}

    public TransactionEntity(BigDecimal amount, LocalDate transactionDate, CostumerEntity costumer) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.costumer = costumer;
    }

    public TransactionEntity(BigDecimal amount, LocalDate transactionDate, TaxType taxType, BigDecimal taxApplied, BigDecimal resultAmount, CostumerEntity costumer) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.taxType = taxType;
        this.taxApplied = taxApplied;
        this.resultAmount = resultAmount;
        this.costumer = costumer;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public TaxType getTaxType() {
        return taxType;
    }

    public BigDecimal getTaxApplied() {
        return taxApplied;
    }

    public BigDecimal getResultAmount() {
        return resultAmount;
    }

    public CostumerEntity getCostumer() {
        return costumer;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setTaxType(TaxType taxType) {
        this.taxType = taxType;
    }

    public void setTaxApplied(BigDecimal taxApplied) {
        this.taxApplied = taxApplied;
    }

    public void setResultAmount(BigDecimal resultAmount) {
        this.resultAmount = resultAmount;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
