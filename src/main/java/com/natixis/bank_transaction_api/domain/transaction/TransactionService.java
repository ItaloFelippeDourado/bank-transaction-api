package com.natixis.bank_transaction_api.domain.transaction;

import com.natixis.bank_transaction_api.application.dtos.TransactionRequest;
import com.natixis.bank_transaction_api.application.dtos.TransactionResponse;
import com.natixis.bank_transaction_api.domain.costumer.CostumerEntity;
import com.natixis.bank_transaction_api.domain.costumer.CostumerService;
import com.natixis.bank_transaction_api.infrastructure.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

    @Autowired
    public TransactionService(TransactionRepository repository, CostumerService costumerService) {
        this.repository = repository;
        this.costumerService = costumerService;
    }


    public TransactionResponse scheduleTransaction(TransactionRequest request) {

        CostumerEntity costumer = costumerService.getLoggedUser();

        TaxType taxType = TaxType.A;
        BigDecimal taxApplied = BigDecimal.ZERO;
        BigDecimal resultAmount = BigDecimal.ZERO;
        long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), request.transactionDate());

        if (request.amount().compareTo(BigDecimal.ZERO) > 0 && request.amount().compareTo(BigDecimal.valueOf(1000)) <= 0) {
            taxApplied = request.amount().multiply(dayPercentageTaxA).add(fixedFee).setScale(2, RoundingMode.HALF_DOWN);
            resultAmount = request.amount().subtract(taxApplied);
        } else if (request.amount().compareTo(BigDecimal.valueOf(1001)) > 0 && request.amount().compareTo(BigDecimal.valueOf(2000)) <= 0) {
            taxType = TaxType.B;
            taxApplied = request.amount().multiply(dayPercentageTaxB).setScale(2, RoundingMode.HALF_DOWN);
            resultAmount = request.amount().subtract(taxApplied);
        } else if (request.amount().compareTo(BigDecimal.valueOf(2001)) > 0) {
            
            if (daysBetween >= 11 && daysBetween <= 20) {
                taxType = TaxType.C1;
                taxApplied = request.amount().multiply(dayPercentageTaxC1).setScale(2, RoundingMode.HALF_DOWN);
                resultAmount = request.amount().subtract(taxApplied);
            } else if (daysBetween >= 21 && daysBetween <= 30) {
                taxType = TaxType.C2;
                taxApplied = request.amount().multiply(dayPercentageTaxC2).setScale(2, RoundingMode.HALF_DOWN);
                resultAmount = request.amount().subtract(taxApplied);
            } else if (daysBetween >= 31 && daysBetween <= 40) {
                taxType = TaxType.C3;
                taxApplied = request.amount().multiply(dayPercentageTaxC3).setScale(2, RoundingMode.HALF_DOWN);
                resultAmount = request.amount().subtract(taxApplied);
            } else if (daysBetween >= 41) {
                taxType = TaxType.C4;
                taxApplied = request.amount().multiply(dayPercentageTaxC4).setScale(2, RoundingMode.HALF_DOWN);
                resultAmount = request.amount().subtract(taxApplied);
            }
        }

        TransactionEntity entity = new TransactionEntity(request.amount(),
                request.transactionDate(),
                taxType,
                taxApplied,
                resultAmount,
                costumer);

        repository.save(entity);

        return new TransactionResponse(entity.getId(), entity.getAmount(), entity.getTransactionDate(), entity.getTaxType(), entity.getTaxApplied(), entity.getResultAmount());
    }
}
