package com.loadpulse.api.application.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;

import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.loadpulse.api.domain.model.LoadTestConfig;

@Service
public class LoadTestEngine {

    private static final String ALLOWED_TARGET = "http://127.0.0.1:8080/api/v1/demo/ping";

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(3))
            .followRedirects(HttpClient.Redirect.NEVER)
            .build();

    public LoadTestResult execute(LoadTestConfig config)
            throws InterruptedException {

        String targetUrl = config.getTargetApi().getBaseUrl();

        // Primeira versão: somente o endpoint demonstrativo local.
        if (!ALLOWED_TARGET.equals(targetUrl)) {
            throw new IllegalArgumentException(
                "Target is not authorized for load testing"
            );
        }

        // Limites conservadores para desenvolvimento local.
        if (config.getVirtualUsers() > 10
                || config.getRequestsPerSecond() > 20
                || config.getDurationSeconds() > 15) {

            throw new IllegalArgumentException(
                "Local demo limits: 10 users, 20 RPS, 15 seconds"
            );
        }

        int virtualUsers = config.getVirtualUsers();
        int requestsPerSecond = config.getRequestsPerSecond();
        int durationSeconds = config.getDurationSeconds();
        int timeoutMs = config.getTimeoutMs();

        AtomicLong successful = new AtomicLong();
        AtomicLong failed = new AtomicLong();

        Semaphore concurrencyLimiter = new Semaphore(virtualUsers);

        long intervalNanos = TimeUnit.SECONDS.toNanos(1) / requestsPerSecond;
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(durationSeconds);
        long nextRequest = System.nanoTime();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            while (System.nanoTime() < deadline) {
                long remaining = nextRequest - System.nanoTime();
                if (remaining > 0) {
                    TimeUnit.NANOSECONDS.sleep(remaining);
                }
                if (System.nanoTime() >= deadline) {
                    break;
                }
                nextRequest += intervalNanos;

                // Descarta a oportunidade de envio caso a
                // concorrência máxima já tenha sido atingida.
                if (!concurrencyLimiter.tryAcquire()) {
                    continue;
                }

                executor.submit(() -> {

                    try {
                        HttpRequest request =
                                HttpRequest.newBuilder()
                                    .uri(URI.create(targetUrl))
                                    .timeout(Duration.ofMillis(timeoutMs))
                                    .GET()
                                    .build();

                        HttpResponse<Void> response = httpClient.send(request,HttpResponse.BodyHandlers.discarding());

                        if (response.statusCode() >= 200 && response.statusCode() < 300) {
                            successful.incrementAndGet();
                        } else {
                            failed.incrementAndGet();
                        }

                    } catch (InterruptedException e) {

                        Thread.currentThread().interrupt();
                        failed.incrementAndGet();

                    } catch (Exception e) {

                        failed.incrementAndGet();

                    } finally {

                        concurrencyLimiter.release();
                    }
                });
            }
        }

        long successCount = successful.get();
        long failureCount = failed.get();

        return new LoadTestResult(
            successCount + failureCount,
            successCount,
            failureCount
        );
    }

    public record LoadTestResult(
            long totalRequests,
            long successfulRequests,
            long failedRequests
    ) {}
}