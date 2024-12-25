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
@Table(name = "review")
@SQLDelete(sql = "UPDATE review SET delete_date = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("delete_date IS NULL")
public class Review extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private Users user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logic_id", insertable = false, updatable = false)
    @JsonBackReference
    private Logic logic;

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
                ", userId=" + (user != null ? user.getId() : "null") +
                ", logicId=" + (logic != null ? logic.getId() : "null") +
                ", content='" + content + '\'' +
                ", " + super.toString() +
                '}';
    }
    
}
