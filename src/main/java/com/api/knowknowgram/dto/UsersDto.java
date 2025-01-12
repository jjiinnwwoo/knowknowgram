package com.api.knowknowgram.dto;

import com.api.knowknowgram.entity.Users;

import lombok.Data;

@Data
public class UsersDto {
    private Long id;
    private String nickname;
    private String email;
    private String photo;
    private Integer point;
    private Integer roleId;

    public UsersDto(Long id, String nickname, String email, String photo, Integer point, Integer roleId) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.photo = photo;
        this.point = point;
    }

    public static UsersDto fromEntity(Users user) {
        return new UsersDto(
            user.getId(),
            user.getNickname(),
            user.getEmail(),
            user.getPhoto(),
            user.getPoint(),
            user.getRole()
        );
    }
}
