package com.zeotap.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeotap.backend.config.SecurityConfig;
import com.zeotap.backend.dto.CreateIncidentRequest;
import com.zeotap.backend.dto.UpdateIncidentRequest;
import com.zeotap.backend.entity.Incident;
import com.zeotap.backend.entity.Severity;
import com.zeotap.backend.entity.Status;
import com.zeotap.backend.repository.IncidentRepository;
import com.zeotap.backend.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class IncidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private String validToken;
    private Incident savedIncident;

    @BeforeEach
    void setUp() {
        incidentRepository.deleteAll();
        validToken = jwtUtil.generateToken("admin");
        savedIncident = incidentRepository.save(Incident.builder()
            .title("Existing incident")
            .service("payment-service")
            .severity(Severity.SEV1)
            .status(Status.OPEN)
            .owner("owner@test.com")
            .summary("Summary")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build());
    }

    @Test
    @DisplayName("GET /api/incidents with token returns paginated list")
    void list_withToken_returnsList() throws Exception {
        mockMvc.perform(get("/api/incidents")
                .header("Authorization", "Bearer " + validToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content.length()").value(greaterThanOrEqualTo(1)))
            .andExpect(jsonPath("$.page").value(0))
            .andExpect(jsonPath("$.size").value(10))
            .andExpect(jsonPath("$.totalElements").value(greaterThanOrEqualTo(1)));
    }

    @Test
    @DisplayName("POST /api/incidents with token creates incident")
    void create_withToken_returns201() throws Exception {
        CreateIncidentRequest request = CreateIncidentRequest.builder()
            .title("New incident")
            .service("auth-service")
            .severity(Severity.SEV2)
            .status(Status.OPEN)
            .build();
        mockMvc.perform(post("/api/incidents")
                .header("Authorization", "Bearer " + validToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.title").value("New incident"))
            .andExpect(jsonPath("$.service").value("auth-service"))
            .andExpect(jsonPath("$.severity").value("SEV2"))
            .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @Test
    @DisplayName("POST /api/incidents with invalid body returns 400")
    void create_invalidBody_returns400() throws Exception {
        String body = "{\"title\":\"\",\"service\":\"auth\",\"severity\":\"SEV1\",\"status\":\"OPEN\"}";
        mockMvc.perform(post("/api/incidents")
                .header("Authorization", "Bearer " + validToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/incidents/:id with token returns incident")
    void getById_withToken_returnsIncident() throws Exception {
        UUID id = savedIncident.getId();
        mockMvc.perform(get("/api/incidents/" + id)
                .header("Authorization", "Bearer " + validToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.title").value("Existing incident"));
    }

    @Test
    @DisplayName("GET /api/incidents/:id with non-existent id returns 404")
    void getById_notFound_returns404() throws Exception {
        UUID randomId = UUID.randomUUID();
        mockMvc.perform(get("/api/incidents/" + randomId)
                .header("Authorization", "Bearer " + validToken))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PATCH /api/incidents/:id with token updates incident")
    void update_withToken_returnsUpdated() throws Exception {
        UUID id = savedIncident.getId();
        UpdateIncidentRequest request = UpdateIncidentRequest.builder()
            .status(Status.RESOLVED)
            .summary("Resolved")
            .build();
        mockMvc.perform(patch("/api/incidents/" + id)
                .header("Authorization", "Bearer " + validToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("RESOLVED"))
            .andExpect(jsonPath("$.summary").value("Resolved"));
    }
}
