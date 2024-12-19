package com.api.knowknowgram.entity;

import jakarta.persistence.*;

import com.api.knowknowgram.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@Table(name = "logic")
@SQLDelete(sql = "UPDATE category SET delete_date = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("delete_date IS NULL")
public class Logic extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rows_num", nullable = false)
    @JsonProperty("rows_num")
    private Integer rowsNum;

    @Column(name = "cols_num", nullable = false)
    @JsonProperty("cols_num")
    private Integer colsNum;

    @Column(name = "row_hints", nullable = false, columnDefinition = "JSONB")
    @JsonProperty("row_hints")
    private String rowHints;

    @Column(name = "col_hints", nullable = false, columnDefinition = "JSONB")
    @JsonProperty("col_hints")
    private String colHints;

    @Column(nullable = false, columnDefinition = "JSONB")
    private String solution;

    // @OneToOne(mappedBy = "logic", fetch = FetchType.LAZY)
    // private GameInfo gameInfo;

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
                ", rowsNum=" + rowsNum +
                ", colsNum=" + colsNum +
                ", rowHints='" + rowHints +
                ", colHints='" + colHints +
                ", solution='" + solution +
                ", " + super.toString() +
                '}';
    }
}
