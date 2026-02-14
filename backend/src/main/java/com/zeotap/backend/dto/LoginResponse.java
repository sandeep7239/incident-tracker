package com.zeotap.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "JWT token response")
public class LoginResponse {

    @Schema(description = "JWT access token - use in Authorization: Bearer <token>")
    private String token;

    @Schema(description = "Token type")
    private String type;

    @Schema(description = "Username of authenticated user")
    private String username;

    @Schema(description = "Token expiry time in milliseconds")
    private Long expiresInMs;
}
