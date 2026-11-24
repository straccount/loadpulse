package com.loadpulse.api.interfaces.rest;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.loadpulse.api.application.dto.*;
import com.loadpulse.api.application.service.LoadTestConfigService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/load-tests")
public class LoadTestConfigController {

    private final LoadTestConfigService service;

    public LoadTestConfigController(
            LoadTestConfigService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LoadTestConfigResponse> create(@Valid @RequestBody CreateLoadTestConfigRequest request) {
        LoadTestConfigResponse response = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/load-tests/" + response.id())).body(response);
    }
}