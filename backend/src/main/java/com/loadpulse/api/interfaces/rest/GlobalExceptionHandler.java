package com.loadpulse.api.interfaces.rest;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.loadpulse.api.application.exception.LoadTestConfigNotFoundException;
import com.loadpulse.api.application.exception.TargetApiNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({TargetApiNotFoundException.class,LoadTestConfigNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleNotFound(RuntimeException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                    "timestamp", OffsetDateTime.now().toString(),
                    "status", 404,
                    "error", "Not Found",
                    "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + ": "+ error.getDefaultMessage()).toList();

        return ResponseEntity.badRequest()
                .body(Map.of(
                        "timestamp", OffsetDateTime.now().toString(),
                        "status", 400,
                        "error", "Bad Request",
                        "messages", errors
                ));
    }

}
