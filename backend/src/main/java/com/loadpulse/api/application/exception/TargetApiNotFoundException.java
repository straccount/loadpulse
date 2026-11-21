package com.loadpulse.api.application.exception;

public class TargetApiNotFoundException extends RuntimeException {

    public TargetApiNotFoundException(Long id) {
        super("Target API not found with ID: " + id);
    }
    
}