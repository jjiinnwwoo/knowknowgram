package com.api.knowknowgram.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogicDto {
    private Integer id;
    private Integer rows;
    private Integer cols;
    private String rowHints;
    private String colHints;
    private String solution;
    private LocalDateTime createDate;

    public LogicDto(Integer id, Integer rows, Integer cols, String rowHints, String colHints, String solution, LocalDateTime createDate) {
        this.id = id;
        this.rows = rows;
        this.cols = cols;
        this.rowHints = rowHints;
        this.colHints = colHints;
        this.solution = solution;
        this.createDate = createDate;
    }
}
