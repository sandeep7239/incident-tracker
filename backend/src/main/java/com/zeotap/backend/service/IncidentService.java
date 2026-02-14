package com.zeotap.backend.service;

import com.zeotap.backend.dto.CreateIncidentRequest;
import com.zeotap.backend.dto.IncidentResponse;
import com.zeotap.backend.dto.PageResponse;
import com.zeotap.backend.dto.UpdateIncidentRequest;
import com.zeotap.backend.entity.Severity;
import com.zeotap.backend.entity.Status;

import java.util.UUID;

public interface IncidentService {

    IncidentResponse create(CreateIncidentRequest request);

    PageResponse<IncidentResponse> findAll(
            int page,
            int size,
            String search,
            String service,
            Severity severity,
            Status status,
            String sortBy,
            String sortDir
    );

    IncidentResponse findById(UUID id);

    IncidentResponse update(UUID id, UpdateIncidentRequest request);
}
