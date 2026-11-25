package com.loadpulse.api.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loadpulse.api.application.dto.CreateLoadTestConfigRequest;
import com.loadpulse.api.application.dto.LoadTestConfigResponse;
import com.loadpulse.api.application.dto.UpdateLoadTestConfigRequest;
import com.loadpulse.api.application.exception.LoadTestConfigNotFoundException;
import com.loadpulse.api.domain.model.LoadTestConfig;
import com.loadpulse.api.domain.model.TargetApi;
import com.loadpulse.api.infrastructure.persistence.LoadTestConfigJpaRepository;

@Service
public class LoadTestConfigService {

    private final LoadTestConfigJpaRepository repository;
    private final TargetApiService targetApiService;

    public LoadTestConfigService(LoadTestConfigJpaRepository repository, TargetApiService targetApiService) {
        this.repository = repository;
        this.targetApiService = targetApiService;
    }

    @Transactional
    public LoadTestConfigResponse create(CreateLoadTestConfigRequest request) {

        TargetApi targetApi = targetApiService.findById(request.targetApiId());

        LoadTestConfig config = new LoadTestConfig(
            targetApi,
            request.name(),
            request.virtualUsers(),
            request.durationSeconds(),
            request.requestsPerSecond(),
            request.timeoutMs()
        );

        return LoadTestConfigResponse.from(repository.save(config));
    }

    @Transactional(readOnly = true)
    public List<LoadTestConfigResponse> findAll() {
        return repository.findAll().stream().map(LoadTestConfigResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public LoadTestConfigResponse findById(Long id) {
        LoadTestConfig config = findEntityById(id);
        return LoadTestConfigResponse.from(config);
    }

    private LoadTestConfig findEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new LoadTestConfigNotFoundException(id));
    }

    @Transactional
    public LoadTestConfigResponse update(Long id, UpdateLoadTestConfigRequest request) {

        LoadTestConfig config = findEntityById(id);

        TargetApi targetApi = targetApiService.findById(request.targetApiId());

        config.update(
                targetApi,
                request.name(),
                request.virtualUsers(),
                request.durationSeconds(),
                request.requestsPerSecond(),
                request.timeoutMs()
        );

        return LoadTestConfigResponse.from(config);
    }

    @Transactional
    public void delete(Long id) {
        LoadTestConfig config = findEntityById(id);
        repository.delete(config);
    }
}