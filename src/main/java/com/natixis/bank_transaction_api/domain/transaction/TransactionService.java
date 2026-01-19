package com.natixis.bank_transaction_api.domain.transaction;

import com.natixis.bank_transaction_api.application.dtos.TransactionRequest;
import com.natixis.bank_transaction_api.application.dtos.TransactionResponse;
import com.natixis.bank_transaction_api.domain.costumer.CostumerEntity;
import com.natixis.bank_transaction_api.domain.costumer.CostumerService;
import com.natixis.bank_transaction_api.infrastructure.mappers.TransactionMapper;
import com.natixis.bank_transaction_api.infrastructure.repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TransactionService {

    @Value("${transaction.fixedFee}")
    private BigDecimal fixedFee;

    @Value("${transaction.dayPercentage.taxA}")
    private BigDecimal dayPercentageTaxA;

    @Value("${transaction.dayPercentage.taxB}")
    private BigDecimal dayPercentageTaxB;

    @Value("${transaction.dayPercentage.taxC1}")
    private BigDecimal dayPercentageTaxC1;

    @Value("${transaction.dayPercentage.taxC2}")
    private BigDecimal dayPercentageTaxC2;

    @Value("${transaction.dayPercentage.taxC3}")
    private BigDecimal dayPercentageTaxC3;

    @Value("${transaction.dayPercentage.taxC4}")
    private BigDecimal dayPercentageTaxC4;

    private final TransactionRepository repository;
    private final CostumerService costumerService;
    private final TransactionMapper mapper;

    @Autowired
    public TransactionService(TransactionRepository repository, CostumerService costumerService, TransactionMapper mapper) {
        this.repository = repository;
        this.costumerService = costumerService;
        this.mapper = mapper;
    }

    public List<TransactionResponse> getScheduledTransactions() {

        CostumerEntity costumer = costumerService.getLoggedUser();

        return mapper.toResponseList(repository.findAllByCostumer(costumer));
    }

    public TransactionResponse scheduleTransaction(TransactionRequest request) {

        CostumerEntity costumer = costumerService.getLoggedUser();

        TransactionEntity entity = new TransactionEntity(request.amount(), request.transactionDate(), costumer);

        calculateTax(entity);

        repository.save(entity);

        return mapper.toResponse(entity);
    }

    public TransactionResponse updateTransaction(UUID transactionId, TransactionRequest request) {

        CostumerEntity loggedUser = costumerService.getLoggedUser();

        TransactionEntity entity = repository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

        if (!entity.getCostumer().getId().equals(loggedUser.getId())) {
            throw new RuntimeException("You are not allowed to update this transaction");
        }

        entity.setAmount(request.amount());
        entity.setTransactionDate(request.transactionDate());

        calculateTax(entity);

        TransactionEntity entityUpdated = repository.save(entity);

        return mapper.toResponse(entityUpdated);
    }

    public void deleteTransaction(UUID transactionId) {
        CostumerEntity loggedUser = costumerService.getLoggedUser();

        TransactionEntity entity = repository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!entity.getCostumer().getId().equals(loggedUser.getId())) {
            throw new RuntimeException("You are not allowed to delete this transaction");
        }

        repository.delete(entity);
    }

    private void calculateTax(TransactionEntity entity) {

        BigDecimal amount = entity.getAmount();
        LocalDate transactionDate = entity.getTransactionDate();

        TaxType taxType = TaxType.A;
        BigDecimal taxApplied = BigDecimal.ZERO;
        BigDecimal resultAmount;

        long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), transactionDate);

        if (amount.compareTo(BigDecimal.ZERO) > 0 &&
                amount.compareTo(BigDecimal.valueOf(1000)) <= 0) {

            taxApplied = amount.multiply(dayPercentageTaxA).add(fixedFee).setScale(2, RoundingMode.HALF_DOWN);

        } else if (amount.compareTo(BigDecimal.valueOf(1001)) >= 0 &&
                amount.compareTo(BigDecimal.valueOf(2000)) <= 0) {
            taxType = TaxType.B;
            taxApplied = amount.multiply(dayPercentageTaxB).setScale(2, RoundingMode.HALF_DOWN);

        } else if (amount.compareTo(BigDecimal.valueOf(2001)) >= 0) {

            if (daysBetween >= 11 && daysBetween <= 20) {
                taxType = TaxType.C1;
                taxApplied = amount.multiply(dayPercentageTaxC1).setScale(2, RoundingMode.HALF_DOWN);

            } else if (daysBetween >= 21 && daysBetween <= 30) {
                taxType = TaxType.C2;
                taxApplied = amount.multiply(dayPercentageTaxC2).setScale(2, RoundingMode.HALF_DOWN);

            } else if (daysBetween >= 31 && daysBetween <= 40) {
                taxType = TaxType.C3;
                taxApplied = amount.multiply(dayPercentageTaxC3).setScale(2, RoundingMode.HALF_DOWN);

            } else if (daysBetween >= 41) {
                taxType = TaxType.C4;
                taxApplied = amount.multiply(dayPercentageTaxC4).setScale(2, RoundingMode.HALF_DOWN);
            }
        }

        resultAmount = amount.subtract(taxApplied);

        entity.setTaxType(taxType);
        entity.setTaxApplied(taxApplied);
        entity.setResultAmount(resultAmount);
    }

    private TransactionResponse toResponse(TransactionEntity entity) {
        return new TransactionResponse(
                entity.getId(),
                entity.getAmount(),
                entity.getTransactionDate(),
                entity.getTaxType(),
                entity.getTaxApplied(),
                entity.getResultAmount()
        );
    }
}
