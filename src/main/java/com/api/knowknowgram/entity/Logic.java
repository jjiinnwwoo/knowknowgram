package com.api.knowknowgram.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "logic")
public class Logic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer rows;

    @Column(nullable = false)
    private Integer cols;

    @Column(name = "row_hints", nullable = false, columnDefinition = "JSONB")
    @JsonProperty("row_hints")
    private String rowHints;

    @Column(name = "col_hints", nullable = false, columnDefinition = "JSONB")
    @JsonProperty("col_hints")
    private String colHints;

    @Column(nullable = false, columnDefinition = "JSONB")
    private String solution;

    @Column(name = "create_date", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @JsonProperty("create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = true)
    @JsonProperty("update_date")
    private LocalDateTime updateDate;

    @Column(name = "delete_date", nullable = true)
    @JsonProperty("delete_date")
    private LocalDateTime deleteDate;

    @OneToOne(mappedBy = "logic", fetch = FetchType.LAZY)
    private GameInfo gameInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logic logic = (Logic) o;
        return id != null ? id.equals(logic.id) : logic.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Logic{" +
                "id=" + id +
                ", rows=" + rows +
                ", cols=" + cols +
                ", rowHints='" + rowHints + '\'' +
                ", colHints='" + colHints + '\'' +
                ", solution='" + solution + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", deleteDate=" + deleteDate +
                ", gameInfo=" + gameInfo +
                '}';
    }
}
