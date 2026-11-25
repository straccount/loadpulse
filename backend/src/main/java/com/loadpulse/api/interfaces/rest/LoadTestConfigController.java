package com.loadpulse.api.interfaces.rest;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loadpulse.api.application.dto.CreateLoadTestConfigRequest;
import com.loadpulse.api.application.dto.LoadTestConfigResponse;
import com.loadpulse.api.application.dto.UpdateLoadTestConfigRequest;
import com.loadpulse.api.application.service.LoadTestConfigService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/load-tests")
public class LoadTestConfigController {

    private final LoadTestConfigService service;

    public LoadTestConfigController(LoadTestConfigService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LoadTestConfigResponse> create(@Valid @RequestBody CreateLoadTestConfigRequest request) {
        LoadTestConfigResponse response = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/load-tests/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<LoadTestConfigResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoadTestConfigResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoadTestConfigResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateLoadTestConfigRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}