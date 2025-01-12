package com.api.knowknowgram.service;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface AuthService {    
    String getAccessTokenByKakao(String code) throws JsonProcessingException;
    JsonNode getUserDetailByKakao(String kakaoAccessToken) throws JsonProcessingException;
    ResponseEntity<?> oauthLogin(String code);
}
