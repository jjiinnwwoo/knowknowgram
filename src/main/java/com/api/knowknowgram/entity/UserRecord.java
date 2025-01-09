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
@Table(name = "user_record")
@SQLDelete(sql = "UPDATE user_record SET delete_date = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("delete_date IS NULL")
public class UserRecord extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logic_id", nullable = false)
    @JsonBackReference
    private Logic logic;

    @Column(nullable = false)
    private int time;

    @Column(nullable = false)
    private Boolean complete = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRecord record = (UserRecord) o;
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
                ", userId=" + (user != null ? user.getId() : "null") +
                ", logicId=" + (logic != null ? logic.getId() : "null") +
                ", time=" + time +
                ", complete=" + complete +
                ", " + super.toString() +
                '}';
    }
    
}
