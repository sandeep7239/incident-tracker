package com.zeotap.backend.repository;

import com.zeotap.backend.entity.Incident;
import com.zeotap.backend.entity.Severity;
import com.zeotap.backend.entity.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("IncidentRepository tests")
class IncidentRepositoryTest {

    @Autowired
    private IncidentRepository incidentRepository;

    @Test
    @DisplayName("save and findById work")
    void saveAndFindById() {
        Incident incident = Incident.builder()
            .title("Repo test")
            .service("payment-service")
            .severity(Severity.SEV1)
            .status(Status.OPEN)
            .build();
        Incident saved = incidentRepository.save(incident);
        assertThat(saved.getId()).isNotNull();

        Optional<Incident> found = incidentRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Repo test");
        assertThat(found.get().getService()).isEqualTo("payment-service");
    }

    @Test
    @DisplayName("findAllFiltered with no filters returns all")
    void findAllFiltered_noFilters() {
        incidentRepository.save(Incident.builder()
            .title("One")
            .service("s1")
            .severity(Severity.SEV1)
            .status(Status.OPEN)
            .build());
        incidentRepository.save(Incident.builder()
            .title("Two")
            .service("s2")
            .severity(Severity.SEV2)
            .status(Status.RESOLVED)
            .build());

        Page<Incident> page = incidentRepository.findAllFiltered(
            null, null, null, null,
            PageRequest.of(0, 10, Sort.by("createdAt").descending()));

        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("findAllFiltered with service filter returns matching")
    void findAllFiltered_serviceFilter() {
        incidentRepository.save(Incident.builder()
            .title("Pay incident")
            .service("payment-service")
            .severity(Severity.SEV1)
            .status(Status.OPEN)
            .build());
        incidentRepository.save(Incident.builder()
            .title("Auth incident")
            .service("auth-service")
            .severity(Severity.SEV2)
            .status(Status.OPEN)
            .build());

        Page<Incident> page = incidentRepository.findAllFiltered(
            null, "payment-service", null, null,
            PageRequest.of(0, 10));

        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getService()).isEqualTo("payment-service");
    }

    @Test
    @DisplayName("findById with non-existent id returns empty")
    void findById_notFound() {
        Optional<Incident> found = incidentRepository.findById(UUID.randomUUID());
        assertThat(found).isEmpty();
    }
}
