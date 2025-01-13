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
    private String provider;
    private String providerId;

    public UsersDto(Long id, String nickname, String email, String photo, Integer point, Integer roleId, String provider, String providerId) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.photo = photo;
        this.point = point;
        this.roleId = roleId;
        this.provider = provider;
        this.providerId = providerId;
    }

    public static UsersDto fromEntity(Users user) {
        return new UsersDto(
            user.getId(),
            user.getNickname(),
            user.getEmail(),
            user.getPhoto(),
            user.getPoint(),
            user.getRoleId(),
            user.getProvider(),
            user.getProviderId()
        );
    }
}
