package com.api.knowknowgram.common.enums;

public enum ERole {
    USER(1),
    ADMIN(2);

    private final int code;

    // 생성자
    ERole(int code) {
        this.code = code;
    }

    // 해당 ERole에 해당하는 code를 가져오는 메서드
    public int getCode() {
        return this.code;
    }

    // code 값을 이용해 ERole을 찾는 메서드
    public static ERole fromCode(int code) {
        for (ERole role : ERole.values()) {
            if (role.getCode() == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role code: " + code);
    }
}
