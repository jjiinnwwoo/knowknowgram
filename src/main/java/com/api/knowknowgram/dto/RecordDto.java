package com.api.knowknowgram.dto;

import com.api.knowknowgram.entity.GameRecord;
import com.api.knowknowgram.entity.GameInfo;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RecordDto {
    private Integer id;
    private Long userId;
    private Integer logicId;
    private Long time;
    private String gameName;
    private String difficulty;
    private int likeCount;
    private LocalDateTime createDate;

    public RecordDto(Integer id, Long userId, Integer logicId, Long time, String gameName, String difficulty, int likeCount, LocalDateTime createDate) {
        this.id = id;
        this.userId = userId;
        this.logicId = logicId;
        this.time = time;
        this.gameName = gameName;
        this.difficulty = difficulty;
        this.likeCount = likeCount;
        this.createDate = createDate;
    }

    public static RecordDto fromEntity(GameRecord record) {        GameInfo gameInfo = record.getLogic().getGameInfo(); // Record 엔티티에서 GameInfo 로직을 가져옵니다.

        return new RecordDto(
                record.getId(),
                record.getUser().getId(),
                record.getLogic().getId(),
                record.getTime(),
                gameInfo.getName(),
                gameInfo.getDifficulty(),
                gameInfo.getLikeCount(),
                record.getCreateDate()
        );
    }
}
