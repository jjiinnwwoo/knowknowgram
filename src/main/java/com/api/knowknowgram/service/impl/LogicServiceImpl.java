package com.api.knowknowgram.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.knowknowgram.common.enums.FilterType;
import com.api.knowknowgram.common.util.Helper;
import com.api.knowknowgram.common.util.LogType;
import com.api.knowknowgram.dto.LogicDto;
import com.api.knowknowgram.entity.Logic;
import com.api.knowknowgram.payload.response.LogicResponse;
import com.api.knowknowgram.payload.response.MyLogicResponse;
import com.api.knowknowgram.repository.logic.LogicRepository;
import com.api.knowknowgram.service.LogicService;

@Service
public class LogicServiceImpl implements LogicService {
    private final LogicRepository logicRepository;

    public LogicServiceImpl(LogicRepository logicRepository) {
        this.logicRepository = logicRepository;
    }

    @Override
    public Page<LogicResponse> getLogic(FilterType filterType, int rowsNum, int colsNum, Pageable pageable) {
        Long currentUserId = Helper.getCurrentUserId();
        Page<Logic> logicPage;

        switch (filterType) {
            case ALL:
                logicPage = logicRepository.findAll(pageable);
                break;
            case POPULAR:
                logicPage = logicRepository.findAllByOrderByLikeCountDesc(currentUserId, pageable);
                break;
            case CREATED_DATE:
                logicPage = logicRepository.findAllByOrderByCreateDateDesc(currentUserId, pageable); 
                break;
            case GRID_SIZE:
                logicPage = logicRepository.findAllByRowsNumAndColsNum(currentUserId, rowsNum, colsNum, pageable);
                break;
            default:
                logicPage = logicRepository.findAll(pageable);
                break;
        }

        Page<LogicResponse> logicResponsePage = logicPage.map(logic -> new LogicResponse(
            logic.getId(),
            logic.getRowsNum(),
            logic.getColsNum(),
            logic.getRowHints(),
            logic.getColHints(),
            logic.getSolution(),
            logic.getCreateDate(),
            logic.getGameInfo(),
            logic.getRecords().isEmpty() ? null : logic.getRecords().get(0)
        ));

        return logicResponsePage;
    }

    
    @Override
    public LogicResponse getLogicById(Long logicId) {
        Logic logic = logicRepository.findById(logicId)
            .orElseGet(() -> {                             
                return new Logic();
            });

        LogicResponse logicResponse = new LogicResponse(
            logic.getId(),
            logic.getRowsNum(),
            logic.getColsNum(),
            logic.getRowHints(),
            logic.getColHints(),
            logic.getSolution(),
            logic.getCreateDate(),
            logic.getGameInfo(),
            logic.getRecords().isEmpty() ? null : logic.getRecords().get(0)
        );
    
        return logicResponse;
    }

    @Override
    public Page<MyLogicResponse> getMyLogic(Pageable pageable) {
        Long currentUserId = Helper.getCurrentUserId();

        Helper.apiLog(LogType.DEV, currentUserId.toString());

        return logicRepository.findAllByUserId(currentUserId, pageable);
    }
}
