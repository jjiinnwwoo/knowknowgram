package com.api.knowknowgram.common.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Helper {
    
    public static void logInfo(String message, Object... params) {
        log.info(message, params);
    }

    public static void logInfo(Object... params) {
        log.info("{}", params);
    }
}
