package com.loadpulse.api.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.loadpulse.api.domain.model.TargetApi;

public interface TargetApiJpaRepository extends JpaRepository<TargetApi, Long> {
}
