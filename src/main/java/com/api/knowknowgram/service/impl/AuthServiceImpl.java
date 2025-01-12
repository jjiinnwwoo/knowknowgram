package com.api.knowknowgram.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.api.knowknowgram.common.exception.NotFoundException;
import com.api.knowknowgram.common.response.JsonResponse;
import com.api.knowknowgram.common.response.ResponseCode;
import com.api.knowknowgram.common.security.JwtUtils;
import com.api.knowknowgram.common.security.UserDetailsImpl;
import com.api.knowknowgram.common.security.CustomUserDetails;
import com.api.knowknowgram.common.util.Helper;
import com.api.knowknowgram.common.util.LogType;
import com.api.knowknowgram.dto.UsersDto;
import com.api.knowknowgram.entity.RefreshToken;
import com.api.knowknowgram.entity.Users;
import com.api.knowknowgram.payload.response.KakaoResponse;
import com.api.knowknowgram.repository.UserRepository;
import com.api.knowknowgram.service.AuthService;
import com.api.knowknowgram.service.RefreshTokenService;
import com.api.knowknowgram.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getAccessTokenByKakao(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "bdd4b7a4544cdbd8f060cfde4852d26f");
        body.add("redirect_uri", "http://localhost:8080/api/auth/test/kakao");
        body.add("code", code);
        body.add("client_secret", "TBn3JNEEdFATMzV8jbVVJsjFegA1nAAt");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Helper.apiLog(LogType.DEV, jsonNode.toString());

        return jsonNode.get("access_token").asText();
    }

    @Transactional
    public JsonNode getUserDetailByKakao(String kakaoAccessToken) throws JsonProcessingException {
        // 1. RestTemplate 객체 생성
        RestTemplate rt = new RestTemplate();

        // 2. 토큰으로 사용자 정보 받기 (PK, Email)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer "+kakaoAccessToken);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                request,
                String.class);

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String email = jsonNode.get("kakao_account").get("email").asText();

        String providerId = jsonNode.get("id").asText();
            
        String provider = "kakao";

        ObjectNode resultNode = objectMapper.createObjectNode();
        resultNode.put("email", email);
        resultNode.put("provider", provider);
        resultNode.put("provider_id", providerId);

        return resultNode;
    }

    @Override
    public ResponseEntity<?> oauthLogin(String code) {
         try {
            // 1. 카카오에서 Access Token 가져오기
            String kakaoAccessToken = getAccessTokenByKakao(code);

            // 2. Access Token으로 카카오 로그인 정보 가져오기
            JsonNode userDetail = authService.getUserDetailByKakao(kakaoAccessToken);

            if (userDetail == null || userDetail.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(JsonResponse.error("카카오 로그인 실패: 이메일 정보를 가져오지 못했습니다."));
            }

            UsersDto user = userService.getUserByEmail(userDetail.get("email").asText());

            if (user == null || user.getEmail() == null || user.getEmail().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(JsonResponse.error("카카오 로그인 실패: 이메일 정보를 가져오지 못했습니다."));
            }

            Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), userDetail.get("provider_id").asText()));
            
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
            
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
            
            ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

            // 5. 성공 응답 반환
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                    .body(JsonResponse.success("로그인 성공"));
        } catch (Exception e) {
            // 6. 에러 로그와 실패 응답 처리
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(JsonResponse.error("서버 오류: " + e.getMessage()));
        }   
    }
}
