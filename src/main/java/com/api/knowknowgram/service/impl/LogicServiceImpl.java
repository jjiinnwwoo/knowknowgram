package com.api.knowknowgram.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.knowknowgram.common.enums.FilterType;
import com.api.knowknowgram.common.util.Helper;
import com.api.knowknowgram.common.util.LogType;
import com.api.knowknowgram.dto.LogicDto;
import com.api.knowknowgram.entity.Logic;
import com.api.knowknowgram.payload.response.LogicResponse;
import com.api.knowknowgram.repository.LogicRepository;
import com.api.knowknowgram.service.LogicService;

@Service
public class LogicServiceImpl implements LogicService {
    private final LogicRepository logicRepository;

    public LogicServiceImpl(LogicRepository logicRepository) {
        this.logicRepository = logicRepository;
    }

    @Override
    public List<LogicResponse> getLogic(FilterType filterType, int rowsNum, int colsNum, Pageable pageable) {
        Long currentUserId = Helper.getCurrentUserId();
        List<Logic> logicList = new ArrayList<>();

        switch (filterType) {
            case ALL:
                logicList = logicRepository.findAll();        
                break;
            case POPULAR:
                logicList = logicRepository.findAllByOrderByLikeCountDesc(currentUserId, pageable).getContent();
                break;
            case CREATED_DATE:
                logicList = logicRepository.findAllByOrderByCreateDateDesc(currentUserId, pageable);
                break;
            case GRID_SIZE:
                logicList = logicRepository.findAllByRowsNumAndColsNum(currentUserId, rowsNum, colsNum, pageable);
                break;        
            default:
                logicList = logicRepository.findAll();
                break;
        }
        
        
        Helper.dataLog(LogType.DEV, logicList);
        
        return logicList.stream()
                .map(logic -> new LogicResponse(
                        logic.getId(),
                        logic.getRowsNum(),
                        logic.getColsNum(),
                        logic.getRowHints(),
                        logic.getColHints(),
                        logic.getSolution(),
                        logic.getCreateDate(),
                        logic.getGameInfo(),
                        logic.getRecords().isEmpty() ? null : logic.getRecords().get(0)
                ))
                .collect(Collectors.toList());
    }
}
