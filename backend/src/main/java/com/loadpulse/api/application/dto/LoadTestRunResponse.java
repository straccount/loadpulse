package com.loadpulse.api.application.dto;

import java.time.OffsetDateTime;

import com.loadpulse.api.domain.model.LoadTestRun;
import com.loadpulse.api.domain.model.LoadTestStatus;

public record LoadTestRunResponse(

    Long id,
    Long loadTestConfigId,
    LoadTestStatus status,
    OffsetDateTime createdAt,
    OffsetDateTime startedAt,
    OffsetDateTime finishedAt,
    String errorMessage

) {

    public static LoadTestRunResponse from(LoadTestRun run) {

        return new LoadTestRunResponse(
            run.getId(),
            run.getConfig().getId(),
            run.getStatus(),
            run.getCreatedAt(),
            run.getStartedAt(),
            run.getFinishedAt(),
            run.getErrorMessage()
        );
    }
}