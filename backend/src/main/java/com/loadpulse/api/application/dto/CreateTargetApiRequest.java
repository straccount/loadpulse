package com.loadpulse.api.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTargetApiRequest(

    @NotBlank
    @Size(max = 150)
    String name,

    @NotBlank
    @Size(max = 2048)
    String baseUrl,

    @Size(max = 500)
    String description

) {

    
}
