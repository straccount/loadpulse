package com.loadpulse.api.interfaces.rest;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loadpulse.api.application.dto.CreateTargetApiRequest;
import com.loadpulse.api.application.service.TargetApiService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/targets")
public class TargetApiController {

    private final TargetApiService service;

    public TargetApiController(TargetApiService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateTargetApiRequest request) {

        var target = service.create(request);

        return ResponseEntity.created(URI.create("/api/v1/targets/" + target.getId())).body(target);
    }
}
