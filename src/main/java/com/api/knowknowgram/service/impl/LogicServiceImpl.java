package com.api.knowknowgram.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.api.knowknowgram.common.util.Helper;
import com.api.knowknowgram.common.util.LogType;
import com.api.knowknowgram.dto.LogicDto;
import com.api.knowknowgram.entity.Logic;
import com.api.knowknowgram.repository.LogicRepository;
import com.api.knowknowgram.service.LogicService;

@Service
public class LogicServiceImpl implements LogicService {
    private final LogicRepository logicRepository;

    public LogicServiceImpl(LogicRepository logicRepository) {
        this.logicRepository = logicRepository;
    }

    @Override
    public List<LogicDto> getAllLogic() {
        List<Logic> logicList = logicRepository.findAll();
        
        Helper.dataLog(LogType.DEV, logicList);
        
        return logicList.stream()
                .map(logic -> new LogicDto(
                        logic.getId(),
                        logic.getRowsNum(),
                        logic.getColsNum(),
                        logic.getRowHints(),
                        logic.getColHints(),
                        logic.getSolution(),
                        logic.getCreateDate()
                ))
                .collect(Collectors.toList());
    }

    // @Override
    // public LogicDto getLogicById(Long id) {
        // return logicList;
        // return logicList.stream()
        //     .filter(logic -> logic.getId().equals(id))
        //     .findFirst()
        //     .orElseThrow(() -> new RuntimeException("Logic not found with id " + id));
    // }

    // @Override
    // public LogicDto createLogic(LogicDto logicDto) {
    //     logicDto.setId(idCounter++);
    //     logicList.add(logicDto);
    //     return logicDto;
    // }
}
