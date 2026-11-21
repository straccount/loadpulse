package com.loadpulse.api.interfaces.rest;

import java.time.OffsetDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.loadpulse.api.application.exception.TargetApiNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TargetApiNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(TargetApiNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                    "timestamp", OffsetDateTime.now().toString(),
                    "status", 404,
                    "error", "Not Found",
                    "message", ex.getMessage()
                ));
    }

}
