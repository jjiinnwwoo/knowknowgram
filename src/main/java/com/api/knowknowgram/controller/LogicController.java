package com.api.knowknowgram.controller;

import org.springframework.web.bind.annotation.RestController;

import com.api.knowknowgram.common.annotation.CommonApiResponses;
import com.api.knowknowgram.common.enums.FilterType;
import com.api.knowknowgram.common.response.JsonResponse;
import com.api.knowknowgram.common.util.Helper;
import com.api.knowknowgram.common.util.LogType;
import com.api.knowknowgram.dto.LogicDto;
import com.api.knowknowgram.payload.request.LogicRequest;
import com.api.knowknowgram.payload.response.LogicResponse;
import com.api.knowknowgram.service.LogicService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/logic")
public class LogicController {
    private final LogicService logicService;
    
    public LogicController(LogicService logicService) {
        this.logicService = logicService;
    }
    
    @GetMapping
    @Operation(summary = "로직 조회", description = "로직을 조회합니다.")
    @CommonApiResponses
    public JsonResponse getLogic(
        @Valid LogicRequest logicRequest, 
        BindingResult bindingResult
        ) {
        if (bindingResult.hasErrors()) {            
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            
            return JsonResponse.error(errorMessage);
        }

        Pageable pageable = PageRequest.of(logicRequest.getPage(), logicRequest.getSize());
        
        List<LogicResponse> logicList = logicService.getLogic(logicRequest.getFilterType(), logicRequest.getRowsNum(), logicRequest.getColsNum(), pageable);

        return JsonResponse.data(logicList);
    }

    // 특정 로직 조회
    // @GetMapping("/{id}")
    // @Operation(summary = "특정 로직 조회", description = "ID를 기반으로 특정 문제를 조회합니다.")
    // @CommonApiResponses
    // public LogicDto getLogicById(@PathVariable Long id) {
    //     return List<LogicDto>();
    // }

    @PostMapping
    @Operation(summary = "로직 생성", description = "로직을 생성합니다.")
    @CommonApiResponses
    public JsonResponse createLogic(@RequestBody LogicDto logicDto) {
        return JsonResponse.success();
    }

    @DeleteMapping
    @Operation(summary = "로직 삭제", description = "로직을 삭제합니다.")
    @CommonApiResponses
    public JsonResponse deleteLogic(@PathVariable Long id) {
        return JsonResponse.success();
    }
}
