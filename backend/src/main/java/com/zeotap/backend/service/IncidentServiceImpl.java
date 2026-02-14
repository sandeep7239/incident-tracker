package com.zeotap.backend.service;

import com.zeotap.backend.constant.CommonConstants;
import com.zeotap.backend.dto.CreateIncidentRequest;
import com.zeotap.backend.dto.IncidentResponse;
import com.zeotap.backend.dto.PageResponse;
import com.zeotap.backend.dto.UpdateIncidentRequest;
import com.zeotap.backend.entity.Incident;
import com.zeotap.backend.entity.Severity;
import com.zeotap.backend.entity.Status;
import com.zeotap.backend.exception.ResourceNotFoundException;
import com.zeotap.backend.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;

    @Override
    @Transactional
    public IncidentResponse create(CreateIncidentRequest request) {
        Incident incident = Incident.builder()
            .title(request.getTitle())
            .service(request.getService())
            .severity(request.getSeverity())
            .status(request.getStatus())
            .owner(request.getOwner())
            .summary(request.getSummary())
            .build();
        incident = incidentRepository.save(incident);
        return IncidentResponse.fromEntity(incident);
    }

    @Override
    public PageResponse<IncidentResponse> findAll(int page, int size, String search,
                                                   String service, Severity severity, Status status,
                                                   String sortBy, String sortDir) {
        Sort sort = sortBy != null && !sortBy.isBlank()
            ? (CommonConstants.SORT_DIR_DESC.equalsIgnoreCase(sortDir) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending())
            : Sort.by(CommonConstants.SORT_FIELD_CREATED_AT).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        String searchTrimmed = (search != null && !search.isBlank()) ? search.trim() : null;
        String serviceFilter = (service != null && !service.isBlank()) ? service.trim() : null;

        Page<Incident> incidentPage = incidentRepository.findAllFiltered(
            searchTrimmed, serviceFilter, severity, status, pageable);

        List<IncidentResponse> content = incidentPage.getContent().stream()
            .map(IncidentResponse::fromEntity)
            .collect(Collectors.toList());

        return PageResponse.<IncidentResponse>builder()
            .content(content)
            .page(incidentPage.getNumber())
            .size(incidentPage.getSize())
            .totalElements(incidentPage.getTotalElements())
            .totalPages(incidentPage.getTotalPages())
            .first(incidentPage.isFirst())
            .last(incidentPage.isLast())
            .build();
    }

    @Override
    public IncidentResponse findById(UUID id) {
        Incident incident = incidentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(CommonConstants.MESSAGE_INCIDENT_NOT_FOUND + id));
        return IncidentResponse.fromEntity(incident);
    }

    @Override
    @Transactional
    public IncidentResponse update(UUID id, UpdateIncidentRequest request) {
        Incident incident = incidentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(CommonConstants.MESSAGE_INCIDENT_NOT_FOUND + id));
        if (request.getTitle() != null) incident.setTitle(request.getTitle());
        if (request.getService() != null) incident.setService(request.getService());
        if (request.getSeverity() != null) incident.setSeverity(request.getSeverity());
        if (request.getStatus() != null) incident.setStatus(request.getStatus());
        if (request.getOwner() != null) incident.setOwner(request.getOwner());
        if (request.getSummary() != null) incident.setSummary(request.getSummary());
        incident = incidentRepository.save(incident);
        return IncidentResponse.fromEntity(incident);
    }

}
