package com.loadpulse.api.domain.model;

import java.time.OffsetDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "load_test_configs")
public class LoadTestConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_api_id", nullable = false)
    private TargetApi targetApi;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "virtual_users", nullable = false)
    private Integer virtualUsers;

    @Column(name = "duration_seconds", nullable = false)
    private Integer durationSeconds;

    @Column(name = "requests_per_second", nullable = false)
    private Integer requestsPerSecond;

    @Column(name = "timeout_ms", nullable = false)
    private Integer timeoutMs;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    protected LoadTestConfig() {
    }

    public LoadTestConfig(
            TargetApi targetApi,
            String name,
            Integer virtualUsers,
            Integer durationSeconds,
            Integer requestsPerSecond,
            Integer timeoutMs) {

        this.targetApi = targetApi;
        this.name = name;
        this.virtualUsers = virtualUsers;
        this.durationSeconds = durationSeconds;
        this.requestsPerSecond = requestsPerSecond;
        this.timeoutMs = timeoutMs;
        this.createdAt = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public TargetApi getTargetApi() {
        return targetApi;
    }

    public String getName() {
        return name;
    }

    public Integer getVirtualUsers() {
        return virtualUsers;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public Integer getRequestsPerSecond() {
        return requestsPerSecond;
    }

    public Integer getTimeoutMs() {
        return timeoutMs;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}