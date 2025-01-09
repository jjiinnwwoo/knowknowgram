package com.api.knowknowgram.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.api.knowknowgram.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@Table(name = "likes")
@SQLDelete(sql = "UPDATE likes SET delete_date = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("delete_date IS NULL")
public class Likes extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logic_id", nullable = false)
    @JsonBackReference
    private Logic logic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Likes likes = (Likes) o;
        return id != null ? id.equals(likes.id) : likes.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Likes{" +
                "id=" + id +
                ", user=" + (user != null ? user.getId() : null) +                
                ", logicId=" + (logic != null ? logic.getId() : null) +
                ", createDate=" + getCreateDate() +
                ", updateDate=" + getUpdateDate() +
                ", deleteDate=" + getDeleteDate() +
                '}';
    }
}
