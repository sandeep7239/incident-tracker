package com.zeotap.backend.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtUtil tests")
class JwtUtilTest {

    private static final String SECRET = "test-secret-key-for-jwt-signing-must-be-at-least-32-chars";
    private static final long EXPIRATION_MS = 3600000L;

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(SECRET, EXPIRATION_MS);
    }

    @Test
    @DisplayName("generateToken produces non-empty token")
    void generateToken_producesToken() {
        String token = jwtUtil.generateToken("admin");
        assertThat(token).isNotBlank();
    }

    @Test
    @DisplayName("validateAndGetUsername returns username for valid token")
    void validateAndGetUsername_validToken_returnsUsername() {
        String token = jwtUtil.generateToken("admin");
        String username = jwtUtil.validateAndGetUsername(token);
        assertThat(username).isEqualTo("admin");
    }

    @Test
    @DisplayName("validateAndGetUsername returns null for invalid token")
    void validateAndGetUsername_invalidToken_returnsNull() {
        String username = jwtUtil.validateAndGetUsername("invalid.jwt.token");
        assertThat(username).isNull();
    }

    @Test
    @DisplayName("validateAndGetUsername returns null for empty token")
    void validateAndGetUsername_emptyToken_returnsNull() {
        String username = jwtUtil.validateAndGetUsername("");
        assertThat(username).isNull();
    }

    @Test
    @DisplayName("different users get different tokens")
    void generateToken_differentUsers_differentTokens() {
        String token1 = jwtUtil.generateToken("admin");
        String token2 = jwtUtil.generateToken("user");
        assertThat(token1).isNotEqualTo(token2);
        assertThat(jwtUtil.validateAndGetUsername(token1)).isEqualTo("admin");
        assertThat(jwtUtil.validateAndGetUsername(token2)).isEqualTo("user");
    }
}
