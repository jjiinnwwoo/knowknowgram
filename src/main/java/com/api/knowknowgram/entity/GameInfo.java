package com.api.knowknowgram.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "game_info")
public class GameInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "logic_id", nullable = false)
    private Integer logicId;

    @Column(nullable = false, length = 50)
    private String difficulty;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer like;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "user_count", nullable = false, columnDefinition = "int default 0")
    private Integer userCount;

    @Column(name = "create_date", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = true)
    private LocalDateTime updateDate;

    @Column(name = "delete_date", nullable = true)
    private LocalDateTime deleteDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Users user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logic_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Logic logic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameInfo gameInfo = (GameInfo) o;
        return id != null ? id.equals(gameInfo.id) : gameInfo.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "id=" + id +
                ", user=" + user +
                ", logicId=" + logicId +
                ", difficulty='" + difficulty + '\'' +
                ", like=" + like +
                ", name='" + name + '\'' +
                ", userCount=" + userCount +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", deleteDate=" + deleteDate +
                ", logic=" + logic +
                '}';
    }
}
