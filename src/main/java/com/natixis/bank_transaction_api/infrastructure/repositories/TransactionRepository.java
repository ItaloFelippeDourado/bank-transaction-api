package com.natixis.bank_transaction_api.infrastructure.repositories;

import com.natixis.bank_transaction_api.domain.costumer.CostumerEntity;
import com.natixis.bank_transaction_api.domain.transaction.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findAllByCostumer(CostumerEntity costumer);
}
