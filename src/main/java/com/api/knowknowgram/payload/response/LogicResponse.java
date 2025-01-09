package com.api.knowknowgram.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LogicResponse {
    private Long id;

    @JsonProperty("rows_num")
    private Integer rowsNum;

    @JsonProperty("cols_num")
    private Integer colsNum; 

    @JsonProperty("is_like")       
    private boolean isLike;
    
    @JsonProperty("is_complete")
    private boolean isComplete;

    public LogicResponse(Long id, Integer rowsNum, Integer colsNum, boolean isLike, boolean isComplete) {
        this.id = id;
        this.rowsNum = rowsNum;
        this.colsNum = colsNum;     
        this.isLike = isLike;        
        this.isComplete = isComplete;        
    }
}