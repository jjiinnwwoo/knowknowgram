package com.api.knowknowgram.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.api.knowknowgram.common.base.BaseEntity;

@Data
@Entity
@Table(name = "review")
@SQLDelete(sql = "UPDATE category SET delete_date = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("delete_date IS NULL")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "logic_id", nullable = false)
    private Integer logicId;

    @Column(nullable = false, length = 300)
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id != null ? id.equals(review.id) : review.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", userId=" + userId +
                ", logicId=" + logicId +
                ", content='" + content +
                ", " + super.toString() +
                '}';
    }
}
