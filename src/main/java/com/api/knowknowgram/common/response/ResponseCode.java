package com.api.knowknowgram.common.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {
    UNKNOWN_ERROR(HttpStatus.NOT_FOUND, "알 수 없는 오류가 발생했습니다."),
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "닉네임이 중복되었습니다."),
    DUPLICATED_USER_USER_ID(HttpStatus.CONFLICT, "아이디가 중복되었습니다."),
    DUPLICATED_USER_PHONE_NUMBER(HttpStatus.CONFLICT, "전화번호가 중복되었습니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    SUCCESS_JOIN(HttpStatus.OK, "회원가입이 성공적으로 완료되었습니다."),
    SUCCESS_USER_INFO(HttpStatus.OK, "사용 가능한 정보입니다."),
    FAIL_JOIN(HttpStatus.BAD_REQUEST, "회원가입에 실패했습니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "기존 비밀번호가 일치하지 않습니다."),
    BOARD_NOT_FOUNDED(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."),
    SUCCESS_PHONE_NUMBER_UPDATE(HttpStatus.OK, "휴대폰번호 변경이 완료되었습니다."),
    SUCCESS_NAME_UPDATE(HttpStatus.OK, "닉네임 변경이 완료되었습니다."),
    SUCCESS_PASSWORD_UPDATE(HttpStatus.OK, "비밀번호 변경이 완료되었습니다."),
    SUCCESS(HttpStatus.OK, "성공"),
    FAIL(HttpStatus.BAD_REQUEST, "실패"),
    DATA_INTEGRITY_VIOLATION(HttpStatus.CONFLICT, "데이터가 손상되었습니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "항목을 찾을 수 없습니다"),
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "올바르지 않은 입력 값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다"),
    SUCCESS_SEND_AUTH_NUMBER(HttpStatus.OK, "인증번호를 발송했습니다."),
    SUCCESS_CHECK_AUTH_NUMBER(HttpStatus.OK, "인증이 완료되었습니다."),
    FAIL_CHECK_AUTH_NUMBER(HttpStatus.OK, "인증번호를 잘못 입력했습니다."),
    FORBIDDEN_CLIENT(HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),
    WRONG_TYPE_TOKEN(HttpStatus.BAD_REQUEST, "올바르지 않은 유형의 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST, "지원되지 않는 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    WRONG_TOKEN(HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰입니다."),
    INVALID_JWT_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "JWT 토큰이 만료되었습니다."),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.BAD_REQUEST, "지원되지 않는 JWT 토큰입니다."),
    EMPTY_JWT_CLAIMS(HttpStatus.BAD_REQUEST, "JWT 클레임 문자열이 비어 있습니다.");
    
    private HttpStatus httpStatus;
    private String message;
}