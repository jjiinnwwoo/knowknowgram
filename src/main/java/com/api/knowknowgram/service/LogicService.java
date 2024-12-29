package com.api.knowknowgram.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.knowknowgram.common.enums.FilterType;
import com.api.knowknowgram.payload.response.LogicResponse;
import com.api.knowknowgram.payload.response.MyLogicResponse;

public interface LogicService {
    Page<LogicResponse> getLogic(FilterType filterType, int rowsNum, int colsNum, Pageable pageable);
    LogicResponse getLogicById(Long logicId);
    Page<MyLogicResponse> getMyLogic(Pageable pageable);
}
