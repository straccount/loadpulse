package com.loadpulse.api.application.exception;

public class LoadTestConfigNotFoundException extends RuntimeException {

    public LoadTestConfigNotFoundException(Long id) {
        super("Load test configuration not found with ID: " + id);
    }
    
}