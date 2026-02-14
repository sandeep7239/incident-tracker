package com.zeotap.backend.dto;

import com.zeotap.backend.constant.CommonConstants;
import com.zeotap.backend.entity.Severity;
import com.zeotap.backend.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = CommonConstants.SCHEMA_DESC_CREATE_REQUEST)
public class CreateIncidentRequest {

    @NotBlank(message = CommonConstants.VALIDATION_TITLE_REQUIRED)
    @Size(max = CommonConstants.LENGTH_TITLE)
    @Schema(description = CommonConstants.SCHEMA_DESC_TITLE, example = CommonConstants.SCHEMA_EXAMPLE_TITLE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @NotBlank(message = CommonConstants.VALIDATION_SERVICE_REQUIRED)
    @Size(max = CommonConstants.LENGTH_SERVICE)
    @Schema(description = CommonConstants.SCHEMA_DESC_SERVICE_NAME, example = CommonConstants.SCHEMA_EXAMPLE_SERVICE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String service;

    @NotNull(message = CommonConstants.VALIDATION_SEVERITY_REQUIRED)
    @Schema(description = CommonConstants.SCHEMA_DESC_SEVERITY, requiredMode = Schema.RequiredMode.REQUIRED)
    private Severity severity;

    @NotNull(message = CommonConstants.VALIDATION_STATUS_REQUIRED)
    @Schema(description = CommonConstants.SCHEMA_DESC_STATUS, requiredMode = Schema.RequiredMode.REQUIRED)
    private Status status;

    @Size(max = CommonConstants.LENGTH_OWNER)
    @Schema(description = CommonConstants.SCHEMA_DESC_OWNER_OPTIONAL)
    private String owner;

    @Schema(description = CommonConstants.SCHEMA_DESC_SUMMARY_OPTIONAL)
    private String summary;
}
