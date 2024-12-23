package com.api.knowknowgram.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.api.knowknowgram.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@Table(name = "game_info")
@SQLDelete(sql = "UPDATE game_info SET delete_date = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("delete_date IS NULL")
public class GameInfo extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "logic_id", nullable = false)
    private Integer logicId;

    @Column(nullable = false, length = 50)
    private String difficulty;

    @Column(name = "like_count", nullable = false, columnDefinition = "int default 0")
    private Integer likeCount;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "user_count", nullable = false, columnDefinition = "int default 0")
    private Integer userCount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logic_id", insertable = false, updatable = false)
    @JsonBackReference
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
                ", logicId=" + logicId +
                ", difficulty='" + difficulty +
                ", likeCount=" + likeCount +
                ", name='" + name +
                ", userCount=" + userCount +                 
                ", " + super.toString() +               
                '}';
    }
}
