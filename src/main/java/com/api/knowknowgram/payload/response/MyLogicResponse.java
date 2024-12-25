package com.api.knowknowgram.payload.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MyLogicResponse {
    private Long id;
    private String gameName;
    private Long recordCount;
    private Long reviewCount;
    private Long completionRate;
    private int shortestTime;
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