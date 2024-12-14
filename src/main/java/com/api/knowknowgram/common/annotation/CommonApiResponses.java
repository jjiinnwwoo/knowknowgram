package com.api.knowknowgram.common.annotation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json")),
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json")),
    @ApiResponse(responseCode = "404", description = "Not Found Exception", content = @Content(mediaType = "application/json")),
    @ApiResponse(responseCode = "406", description = "요청 파라미터가 잘못되었습니다.", content = @Content(mediaType = "application/json")),
    @ApiResponse(responseCode = "429", description = "잠시 후 다시 시도해주세요.", content = @Content(mediaType = "application/json")),
    @ApiResponse(responseCode = "500", description = "시스템 오류", content = @Content(mediaType = "application/json")),
})
public @interface CommonApiResponses {}
