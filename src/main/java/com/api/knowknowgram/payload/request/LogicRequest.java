package com.api.knowknowgram.payload.request;

import com.api.knowknowgram.common.enums.FilterType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogicRequest {
    @Schema(description = "조회 페이지", example = "1", required = false)
    private int page = 0;
    
    @Schema(description = "페이지당 데이터 수", example = "10", required = false)
    private int size = 10;

    @Schema(description = "행 (GRID_SIZE 조회 시)", example = "10", required = false)
    private int rowsNum = 0;

    @Schema(description = "열 (GRID_SIZE 조회 시)", example = "10", required = false)
    private int colsNum = 0;

    @Schema(description = "조회 필터", example = "ALL / POPULAR / CREATED_DATE / GRID_SIZE", required = true)
    @NotNull(message = "FilterType is required.")
    private FilterType filterType;
}