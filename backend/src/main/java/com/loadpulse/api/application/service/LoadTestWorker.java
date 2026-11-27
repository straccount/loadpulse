package com.loadpulse.api.application.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.loadpulse.api.domain.model.LoadTestRun;
import com.loadpulse.api.infrastructure.persistence.LoadTestRunJpaRepository;

@Service
public class LoadTestWorker {

    private final LoadTestEngine engine;
    private final LoadTestRunPersistence persistence;

    public LoadTestWorker(
            LoadTestEngine engine,
            LoadTestRunPersistence persistence) {

        this.engine = engine;
        this.persistence = persistence;
    }

    @Async("loadTestExecutor")
    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT
    )
    public void execute(LoadTestStartedEvent event) {

        Long runId = event.runId();

        try {

            var config = persistence.findConfig(runId);

            var result = engine.execute(config);

            persistence.complete(runId, result);

        } catch (Exception e) {

            persistence.fail(runId, e.getMessage());
        }
    }
}