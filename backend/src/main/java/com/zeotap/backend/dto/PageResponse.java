package com.zeotap.backend.dto;

import com.zeotap.backend.constant.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = CommonConstants.SCHEMA_DESC_PAGE_RESPONSE)
public class PageResponse<T> {

    @Schema(description = CommonConstants.SCHEMA_DESC_CONTENT)
    private List<T> content;

    @Schema(description = CommonConstants.SCHEMA_DESC_PAGE)
    private Integer page;

    @Schema(description = CommonConstants.SCHEMA_DESC_SIZE)
    private Integer size;

    @Schema(description = CommonConstants.SCHEMA_DESC_TOTAL_ELEMENTS)
    private Long totalElements;

    @Schema(description = CommonConstants.SCHEMA_DESC_TOTAL_PAGES)
    private Integer totalPages;

    @Schema(description = CommonConstants.SCHEMA_DESC_FIRST)
    private Boolean first;

    @Schema(description = CommonConstants.SCHEMA_DESC_LAST)
    private Boolean last;
}
