package com.natixis.bank_transaction_api.infrastructure;

import com.natixis.bank_transaction_api.domain.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
}
