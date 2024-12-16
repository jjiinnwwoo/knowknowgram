package com.api.knowknowgram.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String photo;

    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer point;

    @Column(name = "create_date", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @JsonProperty("create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = true)
    @JsonProperty("update_date")
    private LocalDateTime updateDate;

    @Column(name = "delete_date", nullable = true)
    @JsonProperty("delete_date")
    private LocalDateTime deleteeDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Users user = (Users) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", nickname='" + nickname + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' + // password는 @JsonIgnore 어노테이션으로 직렬화에서 제외되므로 실제로는 포함되지 않음
            ", photo='" + photo + '\'' +
            ", point=" + point +
            ", createDate=" + createDate +
            ", updateDate=" + updateDate +
            ", deleteeDate=" + deleteeDate +
            '}';
    }

    /**
     * 닉네임 변경
     */
    public void updateNickname(String nickname) {
        this.nickname = nickname;
        this.updateDate = LocalDateTime.now();
    }

    /**
     * 포인트 추가
     */
    public void increasePoint(Integer point) {
        this.point += point;
        this.updateDate = LocalDateTime.now();
    }

    /**
     * 포인트 차감
     */
    public void decreasePoint(Integer point) {
        this.point -= point;
        this.updateDate = LocalDateTime.now();
    }

    /**
     * 사진 변경
     */
    public void updatePhoto(String photo) {
        this.photo = photo;
        this.updateDate = LocalDateTime.now();
    }
}
