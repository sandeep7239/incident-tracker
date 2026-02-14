package com.zeotap.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Login credentials")
public class LoginRequest {

    @NotBlank(message = "Username is required")
    @Schema(description = "Username", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "Password", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
