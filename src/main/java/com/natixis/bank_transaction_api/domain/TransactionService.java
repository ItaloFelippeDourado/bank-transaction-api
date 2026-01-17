package com.natixis.bank_transaction_api.domain;

import com.natixis.bank_transaction_api.application.dtos.TransactionRequest;
import com.natixis.bank_transaction_api.infrastructure.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
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

    @Autowired
    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void scheduleTransaction(TransactionRequest request) {
        TaxType taxType = TaxType.A;
        BigDecimal taxAmount = BigDecimal.ZERO;
        BigDecimal resultAmount = BigDecimal.ZERO;

        if (request.amount().compareTo(BigDecimal.ZERO) > 0 && request.amount().compareTo(BigDecimal.valueOf(1000)) <= 0) {
            taxAmount = request.amount().multiply(dayPercentageTaxA).add(fixedFee).setScale(2, RoundingMode.HALF_DOWN);
            resultAmount = request.amount().subtract(taxAmount);
        }

        TransactionEntity entity = new TransactionEntity(request.amount(),
                request.transactionDate(),
                taxType,
                taxAmount,
                resultAmount);

        repository.save(entity);
    }
}
