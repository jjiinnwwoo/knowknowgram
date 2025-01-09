package com.api.knowknowgram.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.knowknowgram.common.enums.FilterType;
import com.api.knowknowgram.common.util.Helper;
import com.api.knowknowgram.common.util.LogType;
import com.api.knowknowgram.entity.Logic;
import com.api.knowknowgram.entity.UserRecord;
import com.api.knowknowgram.payload.response.LogicResponse;
import com.api.knowknowgram.payload.response.MyLogicResponse;
import com.api.knowknowgram.payload.response.PaginatedResponse;
import com.api.knowknowgram.repository.logic.LogicRepository;
import com.api.knowknowgram.service.LogicService;

@Service
public class LogicServiceImpl implements LogicService {
    private final LogicRepository logicRepository;

    public LogicServiceImpl(LogicRepository logicRepository) {
        this.logicRepository = logicRepository;
    }

    @Override
    public PaginatedResponse<LogicResponse> getLogic(FilterType filterType, int rowsNum, int colsNum, Pageable pageable) {
        Long currentUserId = Helper.getCurrentUserId();
        Page<Logic> logicPage;

        switch (filterType) {
            case ALL:
                logicPage = logicRepository.findAllByOrderByCreateDateDesc(currentUserId, pageable);
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
                logicPage = logicRepository.findAllByOrderByCreateDateDesc(currentUserId, pageable);
                break;
        }

        Page<LogicResponse> logicResponsePage = logicPage.map(logic -> {
            boolean isLikes = logic.getLikes() != null && !logic.getLikes().isEmpty();                        
            // boolean isComplete = logic.getRecords().isEmpty() ? false : logic.getRecords().get(0).getComplete();
            boolean isComplete = false;
            
            for (UserRecord record : logic.getUserRecords()) {
                if (record.getUser() != null && record.getUser().getId().equals(currentUserId)) {
                    isComplete = record.getComplete();  // 해당 record의 complete 값을 가져오기
                    break;  // 첫 번째로 맞는 record를 찾으면 루프 종료
                }
            }
        
            return new LogicResponse(
                logic.getId(),
                logic.getRowsNum(),
                logic.getColsNum(),
                isLikes,
                isComplete
            );
        });
    
        return new PaginatedResponse<>(logicResponsePage);
    }
    
    
    // @Override
    // public LogicResponse getLogicById(Long logicId) {
    //     Logic logic = logicRepository.findById(logicId)
    //         .orElseGet(() -> {                             
    //             return new Logic();
    //         });

    //     LogicResponse logicResponse = new LogicResponse(
    //         logic.getId(),
    //         logic.getRowsNum(),
    //         logic.getColsNum(),
    //         logic.getRowHints(),
    //         logic.getColHints(),
    //         logic.getSolution(),
    //         logic.getCreateDate(),
    //         logic.getGameInfo(),
    //         logic.getRecords().isEmpty() ? null : logic.getRecords().get(0)
    //     );
    
    //     return logicResponse;
    // }

    @Override
    public Page<MyLogicResponse> getMyLogic(Pageable pageable) {
        Long currentUserId = Helper.getCurrentUserId();

        Helper.apiLog(LogType.DEV, currentUserId.toString());

        return logicRepository.findAllByUserId(currentUserId, pageable);
    }
}
