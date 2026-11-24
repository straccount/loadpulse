package com.loadpulse.api.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.loadpulse.api.domain.model.LoadTestConfig;

public interface LoadTestConfigJpaRepository extends JpaRepository<LoadTestConfig, Long> {
}