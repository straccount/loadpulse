package com.loadpulse.api.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loadpulse.api.domain.model.LoadTestConfig;
import com.loadpulse.api.domain.model.LoadTestRun;
import com.loadpulse.api.infrastructure.persistence.LoadTestRunJpaRepository;

@Service
public class LoadTestRunPersistence {

    private final LoadTestRunJpaRepository repository;

    public LoadTestRunPersistence(LoadTestRunJpaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public LoadTestConfig findConfig(Long runId) {

        LoadTestRun run = repository.findById(runId).orElseThrow();
        // Inicializa a associação antes de sair da transação.
        LoadTestConfig config = run.getConfig();
        config.getTargetApi().getBaseUrl();
        return config;
    }

    @Transactional
    public void complete(Long runId, LoadTestEngine.LoadTestResult result) {

        LoadTestRun run = repository.findById(runId).orElseThrow();
        run.setResults(result.totalRequests(),result.successfulRequests(), result.failedRequests());
        run.complete();
    }

    @Transactional
    public void fail(Long runId, String message) {

        LoadTestRun run = repository.findById(runId).orElseThrow();
        if (message == null) {
            message = "Unknown execution error";
        }
        run.fail(message.substring(0, Math.min(message.length(), 1000)));
    }
}