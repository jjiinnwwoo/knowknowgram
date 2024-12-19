package com.api.knowknowgram.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.api.knowknowgram.common.base.BaseEntity;

@Data
@Entity
@Table(name = "record")
@SQLDelete(sql = "UPDATE category SET delete_date = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("delete_date IS NULL")
public class Record extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "logic_id", nullable = false)
    private Integer logicId;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(nullable = false)
    private Boolean complete = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return id != null ? id.equals(record.id) : record.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", userId=" + userId +
                ", logicId=" + logicId +
                ", time=" + time +
                ", complete=" + complete +
                ", " + super.toString() +
                '}';
    }
}
