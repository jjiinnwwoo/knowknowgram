package com.api.knowknowgram.common.exception;

/*
 * API 호출 제한, 파일 크기 초과, 또는 데이터 처리량 제한과 같은 상황
 */
public class LimitException extends Exception {
    public LimitException() {}

    public LimitException(String message) {
        super(message);
    }

    public LimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public LimitException(Throwable cause) {
        super(cause);
    }

    public LimitException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
