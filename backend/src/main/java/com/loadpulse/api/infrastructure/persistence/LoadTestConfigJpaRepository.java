package com.loadpulse.api.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.loadpulse.api.domain.model.LoadTestConfig;

public interface LoadTestConfigJpaRepository extends JpaRepository<LoadTestConfig, Long> {

    @Override
    @EntityGraph(attributePaths = "targetApi")
    List<LoadTestConfig> findAll();

    @Override
    @EntityGraph(attributePaths = "targetApi")
    Optional<LoadTestConfig> findById(Long id);
    
}