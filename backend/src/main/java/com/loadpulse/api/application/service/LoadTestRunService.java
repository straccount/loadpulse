package com.loadpulse.api.application.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loadpulse.api.application.dto.LoadTestRunResponse;
import com.loadpulse.api.application.exception.LoadTestConfigNotFoundException;
import com.loadpulse.api.application.exception.LoadTestRunNotFoundException;
import com.loadpulse.api.domain.model.LoadTestConfig;
import com.loadpulse.api.domain.model.LoadTestRun;
import com.loadpulse.api.infrastructure.persistence.LoadTestConfigJpaRepository;
import com.loadpulse.api.infrastructure.persistence.LoadTestRunJpaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import com.loadpulse.api.domain.model.LoadTestStatus;

@Service
public class LoadTestRunService {

    private final LoadTestRunJpaRepository runRepository;
    private final LoadTestConfigJpaRepository configRepository;
    private final ApplicationEventPublisher eventPublisher;

    public LoadTestRunService(
            LoadTestRunJpaRepository runRepository,
            LoadTestConfigJpaRepository configRepository,
            ApplicationEventPublisher eventPublisher) {

        this.runRepository = runRepository;
        this.configRepository = configRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public LoadTestRunResponse create(Long configId) {
        LoadTestConfig config = configRepository.findById(configId).orElseThrow(() -> new LoadTestConfigNotFoundException(configId));
        LoadTestRun run = new LoadTestRun(config);
        return LoadTestRunResponse.from(runRepository.save(run));
    }

    @Transactional(readOnly = true)
    public LoadTestRunResponse findById(Long runId) {
        LoadTestRun run = runRepository.findById(runId).orElseThrow(() -> new LoadTestRunNotFoundException(runId));
        return LoadTestRunResponse.from(run);
    }

    @Transactional
    public LoadTestRunResponse start(Long runId) {

        LoadTestRun run = runRepository.findById(runId).orElseThrow(() -> new LoadTestRunNotFoundException(runId));
        run.start();
        runRepository.saveAndFlush(run);
        eventPublisher.publishEvent(new LoadTestStartedEvent(runId));
        return LoadTestRunResponse.from(run);
    }    
}