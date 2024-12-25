package com.api.knowknowgram.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Data
public class MyLogicRequest {
    @Schema(description = "조회 페이지", example = "1", required = false)
    private int page = 0;
    
    @Schema(description = "페이지당 데이터 수", example = "10", required = false)
    private int size = 10;
}