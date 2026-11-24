package com.loadpulse.api.application.dto;

import java.time.OffsetDateTime;
import com.loadpulse.api.domain.model.LoadTestConfig;

public record LoadTestConfigResponse(

    Long id,
    Long targetApiId,
    String name,
    Integer virtualUsers,
    Integer durationSeconds,
    Integer requestsPerSecond,
    Integer timeoutMs,
    OffsetDateTime createdAt

) {

    public static LoadTestConfigResponse from(LoadTestConfig config) {

        return new LoadTestConfigResponse(
            config.getId(),
            config.getTargetApi().getId(),
            config.getName(),
            config.getVirtualUsers(),
            config.getDurationSeconds(),
            config.getRequestsPerSecond(),
            config.getTimeoutMs(),
            config.getCreatedAt()
        );
    }
}