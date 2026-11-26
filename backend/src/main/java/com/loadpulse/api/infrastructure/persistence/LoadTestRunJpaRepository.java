package com.loadpulse.api.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.loadpulse.api.domain.model.LoadTestRun;

public interface LoadTestRunJpaRepository extends JpaRepository<LoadTestRun, Long> {
}