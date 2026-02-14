package com.zeotap.backend.service;

import com.zeotap.backend.dto.CreateIncidentRequest;
import com.zeotap.backend.dto.IncidentResponse;
import com.zeotap.backend.dto.PageResponse;
import com.zeotap.backend.dto.UpdateIncidentRequest;
import com.zeotap.backend.entity.Incident;
import com.zeotap.backend.entity.Severity;
import com.zeotap.backend.entity.Status;
import com.zeotap.backend.exception.ResourceNotFoundException;
import com.zeotap.backend.repository.IncidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("IncidentServiceImpl tests")
class IncidentServiceImplTest {

    @Mock
    private IncidentRepository incidentRepository;

    @InjectMocks
    private IncidentServiceImpl incidentService;

    private Incident incident;
    private UUID incidentId;

    @BeforeEach
    void setUp() {
        incidentId = UUID.randomUUID();
        incident = Incident.builder()
            .id(incidentId)
            .title("Test incident")
            .service("payment-service")
            .severity(Severity.SEV1)
            .status(Status.OPEN)
            .owner("admin@test.com")
            .summary("Summary")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    @Test
    @DisplayName("create saves incident and returns response")
    void create_returnsResponse() {
        CreateIncidentRequest request = CreateIncidentRequest.builder()
            .title("New incident")
            .service("auth-service")
            .severity(Severity.SEV2)
            .status(Status.OPEN)
            .owner("user@test.com")
            .summary("Desc")
            .build();
        Incident saved = Incident.builder()
            .id(incidentId)
            .title(request.getTitle())
            .service(request.getService())
            .severity(request.getSeverity())
            .status(request.getStatus())
            .owner(request.getOwner())
            .summary(request.getSummary())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        when(incidentRepository.save(any(Incident.class))).thenReturn(saved);

        IncidentResponse response = incidentService.create(request);

        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("New incident");
        assertThat(response.getService()).isEqualTo("auth-service");
        assertThat(response.getSeverity()).isEqualTo(Severity.SEV2);
        assertThat(response.getStatus()).isEqualTo(Status.OPEN);
        assertThat(response.getOwner()).isEqualTo("user@test.com");
        assertThat(response.getSummary()).isEqualTo("Desc");
        verify(incidentRepository).save(any(Incident.class));
    }

    @Test
    @DisplayName("findAll returns paginated response")
    void findAll_returnsPaginatedResponse() {
        Page<Incident> page = new PageImpl<>(List.of(incident), PageRequest.of(0, 10), 1);
        when(incidentRepository.findAllFiltered(eq(null), eq(null), eq(null), eq(null), any(Pageable.class)))
            .thenReturn(page);

        PageResponse<IncidentResponse> result = incidentService.findAll(0, 10, null, null, null, null, null, "desc");

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Test incident");
        assertThat(result.getPage()).isZero();
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getFirst()).isTrue();
        assertThat(result.getLast()).isTrue();
        verify(incidentRepository).findAllFiltered(eq(null), eq(null), eq(null), eq(null), any(Pageable.class));
    }

    @Test
    @DisplayName("findAll with search trims and passes params")
    void findAll_withSearch_passesTrimmedParams() {
        Page<Incident> page = new PageImpl<>(List.of());
        when(incidentRepository.findAllFiltered(eq("pay"), eq("payment-service"), eq(Severity.SEV1), eq(Status.OPEN), any(Pageable.class)))
            .thenReturn(page);

        incidentService.findAll(0, 5, "  pay  ", "  payment-service  ", Severity.SEV1, Status.OPEN, "title", "asc");

        verify(incidentRepository).findAllFiltered(eq("pay"), eq("payment-service"), eq(Severity.SEV1), eq(Status.OPEN), any(Pageable.class));
    }

    @Test
    @DisplayName("findById returns response when incident exists")
    void findById_whenExists_returnsResponse() {
        when(incidentRepository.findById(incidentId)).thenReturn(Optional.of(incident));

        IncidentResponse response = incidentService.findById(incidentId);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(incidentId);
        assertThat(response.getTitle()).isEqualTo("Test incident");
        verify(incidentRepository).findById(incidentId);
    }

    @Test
    @DisplayName("findById throws when incident not found")
    void findById_whenNotFound_throws() {
        when(incidentRepository.findById(incidentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> incidentService.findById(incidentId))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining(incidentId.toString());
        verify(incidentRepository).findById(incidentId);
    }

    @Test
    @DisplayName("update modifies and returns incident")
    void update_returnsUpdatedResponse() {
        when(incidentRepository.findById(incidentId)).thenReturn(Optional.of(incident));
        when(incidentRepository.save(any(Incident.class))).thenAnswer(inv -> inv.getArgument(0));
        UpdateIncidentRequest request = UpdateIncidentRequest.builder()
            .status(Status.RESOLVED)
            .summary("Resolved")
            .build();

        IncidentResponse response = incidentService.update(incidentId, request);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(Status.RESOLVED);
        assertThat(response.getSummary()).isEqualTo("Resolved");
        verify(incidentRepository).findById(incidentId);
        verify(incidentRepository).save(any(Incident.class));
    }

    @Test
    @DisplayName("update throws when incident not found")
    void update_whenNotFound_throws() {
        when(incidentRepository.findById(incidentId)).thenReturn(Optional.empty());
        UpdateIncidentRequest request = UpdateIncidentRequest.builder().status(Status.RESOLVED).build();

        assertThatThrownBy(() -> incidentService.update(incidentId, request))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining(incidentId.toString());
        verify(incidentRepository).findById(incidentId);
        verify(incidentRepository, org.mockito.Mockito.never()).save(any());
    }
}
