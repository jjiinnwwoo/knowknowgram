package com.api.knowknowgram.payload.response;

import lombok.Data;

import java.time.LocalDateTime;

import com.api.knowknowgram.entity.GameInfo;
import com.api.knowknowgram.entity.Record;

@Data
public class LogicResponse {
    private Long id;
    private Integer rowsNum;
    private Integer colsNum;
    private String rowHints;
    private String colHints;
    private String solution;
    private LocalDateTime createDate;
    private GameInfo gameInfo;
    private Record record;

    public LogicResponse(Long id, Integer rowsNum, Integer colsNum, String rowHints, String colHints, String solution, LocalDateTime createDate, GameInfo gameInfo, Record record) {
        this.id = id;
        this.rowsNum = rowsNum;
        this.colsNum = colsNum;
        this.rowHints = rowHints;
        this.colHints = colHints;
        this.solution = solution;
        this.createDate = createDate;
        this.gameInfo = gameInfo;
        this.record = record;
    }
}