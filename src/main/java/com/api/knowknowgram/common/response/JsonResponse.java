package com.api.knowknowgram.common.response;

import lombok.Data;

@Data
public class JsonResponse {
    private String status;
    private String message;
    private Object data;

    public JsonResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static JsonResponse success(String message) {
        return new JsonResponse("success", message, null);
    }

    public static JsonResponse success() {
        return new JsonResponse("success", "", null);
    }

    public static JsonResponse data(Object data) {
        return new JsonResponse("success", "", data);
    }

    public static JsonResponse error(String message, String status) {
        return new JsonResponse(status, message, null);
    }

    public static JsonResponse error(String message) {
        return new JsonResponse("fail", message, null);
    }

    public static JsonResponse error(String message, Object data) {
        return new JsonResponse("fail", message, data);
    }
}
