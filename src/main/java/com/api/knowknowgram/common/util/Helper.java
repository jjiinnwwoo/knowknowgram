package com.api.knowknowgram.common.util;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.knowknowgram.common.security.UserDetailsImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;

public class Helper {
    private static final Logger apiLogger = LoggerFactory.getLogger("com.api.knowknowgram.api");
    private static final Logger dataLogger = LoggerFactory.getLogger("com.api.knowknowgram.data");

    public static void apiLog(LogType type, String message) {

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        StackTraceElement caller = stackTraceElements[2];

        String fileName = caller.getFileName();
        String methodName = caller.getMethodName(); 

        // 로그인 기능 활성화 후 주석 제거
        // apiLogger.info("{} {} {} {} {} {}", type.getShortName(), fileName, methodName, timestamp,
        //         getCurrentUserId(), message);

        apiLogger.info("{} {} {} {} {}", type.getShortName(), fileName, methodName, timestamp, message);
    }

    public static void dataLog(LogType logType, Object data) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTraceElements[2];

        String callerFileName = caller.getFileName();
        String callerClassName = caller.getClassName();
        String callerMethodName = caller.getMethodName();
        int callerLineNumber = caller.getLineNumber();

        dataLogger.atInfo()
                .addKeyValue("log_type", logType)
                .addKeyValue("log_file", callerFileName)
                .addKeyValue("log_class", callerClassName)
                .addKeyValue("log_method", callerMethodName)
                .addKeyValue("log_line", callerLineNumber)
                // .addKeyValue("user_id", getCurrentUserId())  // 로그인 기능 활성화 후 주석 제거
                .addKeyValue("data", data).log();
    }

    // 현재 인증된 사용자 ID 조회
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            apiLog(LogType.DEV, userDetails.toString());    
            return userDetails.getId();
        }

        throw new RuntimeException("User not authenticated");
    }
}
