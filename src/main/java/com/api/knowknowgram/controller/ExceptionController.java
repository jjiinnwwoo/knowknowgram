package com.api.knowknowgram.controller;

import com.api.knowknowgram.common.exception.LimitException;
import com.api.knowknowgram.common.exception.NotFoundException;
import com.api.knowknowgram.common.exception.ServiceException;
import com.api.knowknowgram.common.response.JsonResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import java.util.List;

/**
 * 전역 예외 처리를 담당하는 컨트롤러입니다.
 */
@RestControllerAdvice
@Slf4j
public class ExceptionController {

    // 일반 예외 처리
    @ExceptionHandler(Exception.class)
    public JsonResponse handleGeneralException(Exception e) {
        log.error("시스템 에러 발생", e);
        return JsonResponse.error("시스템 오류", 500);
    }

    // 서비스 관련 예외 처리
    @ExceptionHandler(ServiceException.class)
    public JsonResponse handleServiceException(ServiceException e) {
        return JsonResponse.error(e.getMessage(), 500);
    }

    // Redis 연결 실패 예외 처리
    @ExceptionHandler(RedisConnectionFailureException.class)
    public JsonResponse handleRedisException(RedisConnectionFailureException e) {
        return JsonResponse.error("Redis 서비스 연결 실패", 500);
    }

    // 클라이언트가 잘못된 JSON 데이터를 보냈을 때
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonResponse handleInvalidJson(HttpMessageNotReadableException e) {
        log.error("JSON 파싱 오류", e);
        return JsonResponse.error("요청 데이터 파싱 실패", 406);
    }

    // 요청 파라미터 유효성 검사 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResponse handleValidationException(MethodArgumentNotValidException e) {
        StringBuilder errorMsg = new StringBuilder();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        for (ObjectError error : allErrors) {
            errorMsg.append(error.getDefaultMessage()).append(", ");
        }
        String msg = errorMsg.substring(0, errorMsg.length() - 2); // 마지막 ", " 제거
        return JsonResponse.error(msg, 406);
    }

    // 지원하지 않는 HTTP 메서드 요청 처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public JsonResponse handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        return JsonResponse.error("요청 메서드가 잘못되었습니다.", 400);
    }

    // 잘못된 파라미터 타입 요청 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public JsonResponse handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        return JsonResponse.error("잘못된 요청입니다.", 400);
    }

    // 필수 파라미터 누락 처리
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public JsonResponse handleMissingParameter(MissingServletRequestParameterException e) {
        return JsonResponse.error("요청 파라미터가 잘못되었습니다.", 406);
    }

    // 리소스를 찾을 수 없을 때
    @ExceptionHandler(NotFoundException.class)
    public JsonResponse handleNotFoundException(NotFoundException e) {
        return JsonResponse.error(e.getMessage(), 404);
    }

    // 요청 제한 초과 예외 처리
    @ExceptionHandler(LimitException.class)
    public JsonResponse handleLimitException(LimitException e) {
        return JsonResponse.error("잠시 후 다시 시도해주세요.", 429);
    }

    // Amazon S3 관련 예외 처리
    // @ExceptionHandler(AmazonS3Exception.class)
    // public JsonResponse handleAmazonS3Exception(AmazonS3Exception e) {
    //     log.error("S3 에러 발생: {}", e.getMessage());
    //     return JsonResponse.error("스토리지 설정이 잘못되었거나 접근할 수 없습니다.", 500);
    // }
}
