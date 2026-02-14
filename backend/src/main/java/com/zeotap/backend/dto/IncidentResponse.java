package com.zeotap.backend.dto;

import com.zeotap.backend.constant.CommonConstants;
import com.zeotap.backend.entity.Incident;
import com.zeotap.backend.entity.Severity;
import com.zeotap.backend.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = CommonConstants.SCHEMA_DESC_INCIDENT_RESPONSE)
public class IncidentResponse {

    @Schema(description = CommonConstants.SCHEMA_DESC_ID)
    private UUID id;

    @Schema(description = CommonConstants.SCHEMA_DESC_TITLE)
    private String title;

    @Schema(description = CommonConstants.SCHEMA_DESC_SERVICE)
    private String service;

    @Schema(description = CommonConstants.SCHEMA_DESC_SEVERITY)
    private Severity severity;

    @Schema(description = CommonConstants.SCHEMA_DESC_STATUS)
    private Status status;

    @Schema(description = CommonConstants.SCHEMA_DESC_OWNER)
    private String owner;

    @Schema(description = CommonConstants.SCHEMA_DESC_SUMMARY)
    private String summary;

    @Schema(description = CommonConstants.SCHEMA_DESC_CREATED_AT)
    private LocalDateTime createdAt;

    @Schema(description = CommonConstants.SCHEMA_DESC_UPDATED_AT)
    private LocalDateTime updatedAt;

    public static IncidentResponse fromEntity(Incident entity) {
        return IncidentResponse.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .service(entity.getService())
            .severity(entity.getSeverity())
            .status(entity.getStatus())
            .owner(entity.getOwner())
            .summary(entity.getSummary())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }
}
