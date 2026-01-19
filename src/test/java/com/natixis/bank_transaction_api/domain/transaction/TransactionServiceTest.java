package com.natixis.bank_transaction_api.domain.transaction;

import com.natixis.bank_transaction_api.application.dtos.TransactionResponse;
import com.natixis.bank_transaction_api.domain.costumer.CostumerEntity;
import com.natixis.bank_transaction_api.domain.costumer.CostumerService;
import com.natixis.bank_transaction_api.infrastructure.mappers.TransactionMapper;
import com.natixis.bank_transaction_api.infrastructure.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private CostumerService costumerService;

    @Mock
    private TransactionMapper mapper;

    @InjectMocks
    private TransactionService transactionService;

    private CostumerEntity costumer;
    private TransactionEntity transaction;
    private TransactionResponse transactionResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        costumer = new CostumerEntity("natixis.test", "natixis@test.com", "hashedPassword");
        costumer.setId(UUID.randomUUID());

        transaction = new TransactionEntity(
                new BigDecimal("100.00"),
                LocalDate.now().plusDays(5), costumer);
        transaction.setId(UUID.randomUUID());

        transactionResponse = new TransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getTransactionDate(),
                transaction.getTaxType(),
                transaction.getTaxApplied(),
                transaction.getResultAmount()
        );
    }

    @Test
    void testGetScheduledTransactions() {

        when(costumerService.getLoggedUser()).thenReturn(costumer);
        when(repository.findAllByCostumer(costumer)).thenReturn(List.of(transaction));
        when(mapper.toResponseList(List.of(transaction))).thenReturn(List.of(transactionResponse));

        List<TransactionResponse> result = transactionService.getScheduledTransactions();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(transaction.getId(), result.get(0).id());

        verify(costumerService, times(1)).getLoggedUser();
        verify(repository, times(1)).findAllByCostumer(costumer);
        verify(mapper, times(1)).toResponseList(List.of(transaction));
    }
}