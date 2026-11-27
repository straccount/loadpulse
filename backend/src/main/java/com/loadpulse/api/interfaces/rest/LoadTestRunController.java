package com.loadpulse.api.interfaces.rest;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.loadpulse.api.application.dto.LoadTestRunResponse;
import com.loadpulse.api.application.service.LoadTestRunService;

@RestController
@RequestMapping("/api/v1")
public class LoadTestRunController {

    private final LoadTestRunService service;

    public LoadTestRunController(LoadTestRunService service) {
        this.service = service;
    }

    @PostMapping("/load-tests/{configId}/runs")
    public ResponseEntity<LoadTestRunResponse> create(@PathVariable Long configId) {
        LoadTestRunResponse response = service.create(configId);
        return ResponseEntity.created(URI.create("/api/v1/runs/" + response.id())).body(response);
    }

    @GetMapping("/runs/{runId}")
    public ResponseEntity<LoadTestRunResponse> findById(@PathVariable Long runId) {
        return ResponseEntity.ok(service.findById(runId));
    }

    @PostMapping("/runs/{runId}/start")
    public ResponseEntity<LoadTestRunResponse> start(@PathVariable Long runId) {
        LoadTestRunResponse response = service.start(runId);
        return ResponseEntity.accepted().body(response);
    }
}