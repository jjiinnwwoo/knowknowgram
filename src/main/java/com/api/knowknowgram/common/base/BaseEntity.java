package com.api.knowknowgram.common.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "create_date", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @JsonProperty("create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = true)
    @JsonProperty("update_date")
    private LocalDateTime updateDate;

    @Column(name = "delete_date", nullable = true)
    @JsonProperty("delete_date")
    private LocalDateTime deleteDate;    

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public LocalDateTime getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(LocalDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }

    @Override
    public String toString() {
        return "createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", deleteDate=" + deleteDate;
    }
}
