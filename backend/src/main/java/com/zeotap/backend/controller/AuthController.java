package com.zeotap.backend.controller;

import com.zeotap.backend.constant.ApiConstants;
import com.zeotap.backend.dto.LoginRequest;
import com.zeotap.backend.dto.LoginResponse;
import com.zeotap.backend.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.API_AUTH)
@RequiredArgsConstructor
@Tag(name = ApiConstants.SWAGGER_TAG_AUTH, description = ApiConstants.SWAGGER_TAG_AUTH_DESCRIPTION)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Value("${jwt.expiration-ms:86400000}")
    private long expirationMs;

    @PostMapping("/login")
    @Operation(summary = ApiConstants.SWAGGER_LOGIN_SUMMARY, description = ApiConstants.SWAGGER_LOGIN_DESCRIPTION)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(user.getUsername());
        LoginResponse response = LoginResponse.builder()
            .token(token)
            .type("Bearer")
            .username(user.getUsername())
            .expiresInMs(expirationMs)
            .build();
        return ResponseEntity.ok(response);
    }
}
