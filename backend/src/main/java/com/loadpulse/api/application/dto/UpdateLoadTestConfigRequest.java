package com.loadpulse.api.application.dto;

import jakarta.validation.constraints.*;

public record UpdateLoadTestConfigRequest(

    @NotNull
    @Min(1)
    Long targetApiId,

    @NotBlank
    @Size(max = 150)
    String name,

    @NotNull
    @Min(1)
    @Max(100)
    Integer virtualUsers,

    @NotNull
    @Min(1)
    @Max(3600)
    Integer durationSeconds,

    @NotNull
    @Min(1)
    @Max(1000)
    Integer requestsPerSecond,

    @NotNull
    @Min(100)
    @Max(60000)
    Integer timeoutMs

) {
    
}