package com.zeotap.backend.controller;

import com.zeotap.backend.constant.ApiConstants;
import com.zeotap.backend.dto.CreateIncidentRequest;
import com.zeotap.backend.dto.IncidentResponse;
import com.zeotap.backend.dto.PageResponse;
import com.zeotap.backend.dto.UpdateIncidentRequest;
import com.zeotap.backend.entity.Severity;
import com.zeotap.backend.entity.Status;
import com.zeotap.backend.service.IncidentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(ApiConstants.API_INCIDENTS)
@RequiredArgsConstructor
@Tag(name = ApiConstants.SWAGGER_TAG_NAME, description = ApiConstants.SWAGGER_TAG_DESCRIPTION)
public class IncidentController {

    private final IncidentService incidentService;

    @PostMapping
    @Operation(summary = ApiConstants.SWAGGER_CREATE_SUMMARY, description = ApiConstants.SWAGGER_CREATE_DESCRIPTION)
    public ResponseEntity<IncidentResponse> create(@Valid @RequestBody CreateIncidentRequest request) {
        IncidentResponse created = incidentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = ApiConstants.SWAGGER_LIST_SUMMARY, description = ApiConstants.SWAGGER_LIST_DESCRIPTION)
    public ResponseEntity<PageResponse<IncidentResponse>> list(
        @Parameter(description = ApiConstants.SWAGGER_PARAM_PAGE) @RequestParam(name = ApiConstants.PARAM_PAGE, defaultValue = ApiConstants.DEFAULT_PAGE) int page,
        @Parameter(description = ApiConstants.SWAGGER_PARAM_SIZE) @RequestParam(name = ApiConstants.PARAM_SIZE, defaultValue = ApiConstants.DEFAULT_SIZE) int size,
        @Parameter(description = ApiConstants.SWAGGER_PARAM_SEARCH) @RequestParam(name = ApiConstants.PARAM_SEARCH, required = false) String search,
        @Parameter(description = ApiConstants.SWAGGER_PARAM_SERVICE) @RequestParam(name = ApiConstants.PARAM_SERVICE, required = false) String service,
        @Parameter(description = ApiConstants.SWAGGER_PARAM_SEVERITY) @RequestParam(name = ApiConstants.PARAM_SEVERITY, required = false) Severity severity,
        @Parameter(description = ApiConstants.SWAGGER_PARAM_STATUS) @RequestParam(name = ApiConstants.PARAM_STATUS, required = false) Status status,
        @Parameter(description = ApiConstants.SWAGGER_PARAM_SORT_BY) @RequestParam(name = ApiConstants.PARAM_SORT_BY, required = false) String sortBy,
        @Parameter(description = ApiConstants.SWAGGER_PARAM_SORT_DIR) @RequestParam(name = ApiConstants.PARAM_SORT_DIR, defaultValue = ApiConstants.DEFAULT_SORT_DIR) String sortDir
    ) {
        PageResponse<IncidentResponse> result = incidentService.findAll(page, size, search, service, severity, status, sortBy, sortDir);
        return ResponseEntity.ok(result);
    }

    @GetMapping(ApiConstants.PATH_ID)
    @Operation(summary = ApiConstants.SWAGGER_GET_BY_ID_SUMMARY)
    public ResponseEntity<IncidentResponse> getById(
        @Parameter(description = ApiConstants.SWAGGER_PARAM_ID) @PathVariable(ApiConstants.PARAM_ID) UUID id
    ) {
        IncidentResponse incident = incidentService.findById(id);
        return ResponseEntity.ok(incident);
    }

    @PatchMapping(ApiConstants.PATH_ID)
    @Operation(summary = ApiConstants.SWAGGER_UPDATE_SUMMARY, description = ApiConstants.SWAGGER_UPDATE_DESCRIPTION)
    public ResponseEntity<IncidentResponse> update(
        @Parameter(description = ApiConstants.SWAGGER_PARAM_ID) @PathVariable(ApiConstants.PARAM_ID) UUID id,
        @RequestBody UpdateIncidentRequest request
    ) {
        IncidentResponse updated = incidentService.update(id, request);
        return ResponseEntity.ok(updated);
    }
}
