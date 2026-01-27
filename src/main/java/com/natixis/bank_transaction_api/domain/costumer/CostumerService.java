package com.natixis.bank_transaction_api.domain.costumer;

import com.natixis.bank_transaction_api.application.dtos.CostumerRequest;
import com.natixis.bank_transaction_api.domain.exceptions.UserAlreadyExistsException;
import com.natixis.bank_transaction_api.infrastructure.repositories.CostumerRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CostumerService {

    private final CostumerRepository costumerRepository;
    private final PasswordEncoder passwordEncoder;

    public CostumerService(CostumerRepository costumerRepository, PasswordEncoder passwordEncoder) {
        this.costumerRepository = costumerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(CostumerRequest request) {
        if (costumerRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Costumer already exists!");
        }

        CostumerEntity user = new CostumerEntity(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));

        costumerRepository.save(user);
    }

    public CostumerEntity getLoggedUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return costumerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
