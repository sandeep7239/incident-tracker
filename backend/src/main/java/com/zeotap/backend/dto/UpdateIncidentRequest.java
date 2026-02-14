package com.zeotap.backend.dto;

import com.zeotap.backend.constant.CommonConstants;
import com.zeotap.backend.entity.Severity;
import com.zeotap.backend.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = CommonConstants.SCHEMA_DESC_UPDATE_REQUEST)
public class UpdateIncidentRequest {

    @Size(max = CommonConstants.LENGTH_TITLE)
    @Schema(description = CommonConstants.SCHEMA_DESC_TITLE)
    private String title;

    @Size(max = CommonConstants.LENGTH_SERVICE)
    @Schema(description = CommonConstants.SCHEMA_DESC_SERVICE_NAME)
    private String service;

    @Schema(description = CommonConstants.SCHEMA_DESC_SEVERITY)
    private Severity severity;

    @Schema(description = CommonConstants.SCHEMA_DESC_STATUS)
    private Status status;

    @Size(max = CommonConstants.LENGTH_OWNER)
    @Schema(description = CommonConstants.SCHEMA_DESC_OWNER)
    private String owner;

    @Schema(description = CommonConstants.SCHEMA_DESC_SUMMARY)
    private String summary;
}
