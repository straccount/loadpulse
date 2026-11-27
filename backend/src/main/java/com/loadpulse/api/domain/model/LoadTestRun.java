package com.loadpulse.api.domain.model;

import java.time.OffsetDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "load_test_runs")
public class LoadTestRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "load_test_config_id", nullable = false)
    private LoadTestConfig config;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LoadTestStatus status;

    @Column(name = "started_at")
    private OffsetDateTime startedAt;

    @Column(name = "finished_at")
    private OffsetDateTime finishedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "error_message", length = 1000)
    private String errorMessage;

    @Column(name = "total_requests", nullable = false)
    private Long totalRequests = 0L;

    @Column(name = "successful_requests", nullable = false)
    private Long successfulRequests = 0L;

    @Column(name = "failed_requests", nullable = false)
    private Long failedRequests = 0L;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    protected LoadTestRun() {
    }

    public LoadTestRun(LoadTestConfig config) {
        this.config = config;
        this.status = LoadTestStatus.PENDING;
        this.createdAt = OffsetDateTime.now();
    }

    public void start() {

        if (status != LoadTestStatus.PENDING) {
            throw new IllegalStateException(
                "Only pending tests can be started"
            );
        }

        this.status = LoadTestStatus.RUNNING;
        this.startedAt = OffsetDateTime.now();
    }

    public void complete() {

        if (status != LoadTestStatus.RUNNING) {
            throw new IllegalStateException(
                "Only running tests can be completed"
            );
        }

        this.status = LoadTestStatus.COMPLETED;
        this.finishedAt = OffsetDateTime.now();
    }

    public void fail(String message) {

        if (status != LoadTestStatus.RUNNING) {
            throw new IllegalStateException(
                "Only running tests can fail"
            );
        }

        this.status = LoadTestStatus.FAILED;
        this.finishedAt = OffsetDateTime.now();
        this.errorMessage = message;
    }

    public Long getId() {
        return id;
    }

    public LoadTestConfig getConfig() {
        return config;
    }

    public LoadTestStatus getStatus() {
        return status;
    }

    public OffsetDateTime getStartedAt() {
        return startedAt;
    }

    public OffsetDateTime getFinishedAt() {
        return finishedAt;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Long getTotalRequests() {
        return totalRequests;
    }

    public Long getSuccessfulRequests() {
        return successfulRequests;
    }

    public Long getFailedRequests() {
        return failedRequests;
    }

    public void setResults(
        long totalRequests,
        long successfulRequests,
        long failedRequests) {

    if (totalRequests < 0
            || successfulRequests < 0
            || failedRequests < 0
            || totalRequests != successfulRequests + failedRequests) {

        throw new IllegalArgumentException(
                "Invalid load test metrics"
        );
    }

    this.totalRequests = totalRequests;
    this.successfulRequests = successfulRequests;
    this.failedRequests = failedRequests;
    }
}