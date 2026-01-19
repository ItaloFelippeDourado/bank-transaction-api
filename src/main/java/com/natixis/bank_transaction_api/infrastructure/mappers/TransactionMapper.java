package com.natixis.bank_transaction_api.infrastructure.mappers;

import com.natixis.bank_transaction_api.application.dtos.TransactionResponse;
import com.natixis.bank_transaction_api.domain.transaction.TransactionEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionMapper {

    public TransactionResponse toResponse(TransactionEntity entity) {
        if (entity == null) return null;

        return new TransactionResponse(
                entity.getId(),
                entity.getAmount(),
                entity.getTransactionDate(),
                entity.getTaxType(),
                entity.getTaxApplied(),
                entity.getResultAmount()
        );
    }

    public List<TransactionResponse> toResponseList(List<TransactionEntity> entities) {
        return entities.stream()
                .map(this::toResponse)
                .toList();
    }
}
