package com.loadpulse.api.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loadpulse.api.application.dto.CreateTargetApiRequest;
import com.loadpulse.api.application.exception.TargetApiNotFoundException;
import com.loadpulse.api.domain.model.TargetApi;
import com.loadpulse.api.infrastructure.persistence.TargetApiJpaRepository;

@Service
public class TargetApiService {

    private final TargetApiJpaRepository repository;

    public TargetApiService(TargetApiJpaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TargetApi create(CreateTargetApiRequest request) {

        TargetApi targetApi = new TargetApi(request.name(),request.baseUrl(),request.description());

        return repository.save(targetApi);
    }

    @Transactional(readOnly = true)
    public List<TargetApi> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public TargetApi findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new TargetApiNotFoundException(id));
    }
}
