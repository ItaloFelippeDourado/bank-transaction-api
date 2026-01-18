package com.natixis.bank_transaction_api.infrastructure.repositories;

import com.natixis.bank_transaction_api.domain.costumer.CostumerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CostumerRepository extends JpaRepository<CostumerEntity, UUID> {

    Optional<CostumerEntity> findByUsername(String username);
}
