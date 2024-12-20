package com.api.knowknowgram.common.util;

public enum LogType {
    SEARCH("SEARCH", "SEARCH"),
    LIKE_REACTION("LIKE_REACTION", "LIKE_REACTION"),
    LOGIC_DELETE("LOGIC_DELETE", "LOGIC_DELETE"),
    LOGIC_CREATE("LOGIC_CREATE", "LOGIC_CREATE"),
    DELETE_USER("DELETE_USER", "DELETE_USER"),
    CHANGE_PASSWORD("CHANGE_PASSWORD", "CHANGE_PASSWORD"),
    SIGN_UP("SIGN_UP", "SIGN_UP"),
    LOGIN("LOGIN", "LOGIN"),
    REQUEST_AUTH_CODE("REQUEST_AUTH_CODE", "REQUEST_AUTH_CODE"),
    WRONG_AUTH_CODE("WRONG_AUTH_CODE", "WRONG_AUTH_CODE"),
    VALIDATE_AUTH_CODE("VALIDATE_AUTH_CODE", "VALIDATE_AUTH_CODE"),
    ACCESS_TOKEN_INFO("ACCESS_TOKEN_INFO", "ACCESS_TOKEN_INFO"),
    ERROR("ERROR", "ERROR"),
    DEV("DEV", "DEV");

    private final String shortName;
    private final String type;

    LogType(String shortName, String type) {
        this.shortName = shortName;
        this.type = type;
    }

    public String getShortName() {
        return shortName;
    }

    public String getType() {
        return type;
    }
}
