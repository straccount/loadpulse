package com.loadpulse.api.infrastructure.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(destroyMethod = "close")
    public ExecutorService loadTestExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}