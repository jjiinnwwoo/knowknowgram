package com.api.knowknowgram.payload.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MyLogicResponse {
    private Long id;
    
    @JsonProperty("game_name")
    private String gameName;

    @JsonProperty("record_count")
    private Long recordCount;

    @JsonProperty("review_count")
    private Long reviewCount;

    @JsonProperty("completion_rate")
    private Long completionRate;

    @JsonProperty("shortest_time")
    private int shortestTime;

    @JsonProperty("create_date")
    private LocalDateTime createDate;

    public MyLogicResponse(Long id, String gameName, Long recordCount, Long reviewCount, Long completionRate, int shortestTime, LocalDateTime createDate) {
        this.id = id;
        this.gameName = gameName;
        this.recordCount = recordCount;
        this.reviewCount = reviewCount;
        this.completionRate = completionRate;
        this.shortestTime = shortestTime;
        this.createDate = createDate;
    }
}