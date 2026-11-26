package com.loadpulse.api.application.exception;

public class LoadTestRunNotFoundException extends RuntimeException {
    public LoadTestRunNotFoundException(Long id) {
        super("Load test run not found with ID: " + id);
    }
}