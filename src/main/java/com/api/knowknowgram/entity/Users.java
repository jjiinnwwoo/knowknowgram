package com.api.knowknowgram.entity;

import jakarta.persistence.*;

import com.api.knowknowgram.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE category SET delete_date = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("delete_date IS NULL")
public class Users extends BaseEntity implements Serializable {
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
            ", nickname='" + nickname +
            ", email='" + email +
            ", photo='" + photo +
            ", point=" + point +
            ", " + super.toString() +
            '}';
    }

    /**
     * 닉네임 변경
     */
    public void updateNickname(String nickname) {
        this.nickname = nickname;
        super.setUpdateDate(LocalDateTime.now());
    }

    /**
     * 포인트 추가
     */
    public void increasePoint(Integer point) {
        this.point += point;
        super.setUpdateDate(LocalDateTime.now());
    }

    /**
     * 포인트 차감
     */
    public void decreasePoint(Integer point) {
        this.point -= point;
        super.setUpdateDate(LocalDateTime.now());
    }

    /**
     * 사진 변경
     */
    public void updatePhoto(String photo) {
        this.photo = photo;
        super.setUpdateDate(LocalDateTime.now());
    }
}
