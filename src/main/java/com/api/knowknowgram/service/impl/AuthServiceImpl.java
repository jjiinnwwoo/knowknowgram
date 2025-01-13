package com.api.knowknowgram.service.impl;

import java.util.Collections;

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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.api.knowknowgram.common.enums.ERole;
import com.api.knowknowgram.common.response.JsonResponse;
import com.api.knowknowgram.common.security.JwtUtils;
import com.api.knowknowgram.common.security.UserDetailsImpl;
import com.api.knowknowgram.common.util.Helper;
import com.api.knowknowgram.common.util.LogType;
import com.api.knowknowgram.dto.UsersDto;
import com.api.knowknowgram.entity.RefreshToken;
import com.api.knowknowgram.entity.Users;
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
        body.add("redirect_uri", "http://localhost:8080/api/auth/oauth/login");
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
        Helper.apiLog(LogType.DEV, kakaoAccessToken);
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
    public ResponseEntity<?> oauthLogin(String accessToken) {
        try {
            // // 카카오에서 Access Token 가져오기
            // String kakaoAccessToken = getAccessTokenByKakao(code);

            // Access Token으로 카카오 로그인 정보 가져오기
            JsonNode userDetail = getUserDetailByKakao(accessToken);

            if (userDetail == null || userDetail.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(JsonResponse.error("카카오 로그인 실패: 이메일 정보를 가져오지 못했습니다."));
            }
        
            UserDetailsImpl userDetailsImpl = null;
            
            try {                                
                UsersDto user = userService.getUserByEmail(userDetail.get("email").asText());
                
                Users newUser = new Users();
                newUser.setId(user.getId());
                newUser.setEmail(user.getEmail());
                newUser.setProvider(user.getProvider());
                newUser.setProviderId(user.getProviderId());                
                newUser.setRoleId(user.getRoleId());

                userDetailsImpl = UserDetailsImpl.build(newUser);
            } catch (Exception e) {
                // 사용자 없으면 새로 저장
                Users newUser = new Users();
                newUser.setEmail(userDetail.get("email").asText());
                newUser.setProvider(userDetail.get("provider").asText());
                newUser.setProviderId(userDetail.get("provider_id").asText());                
                newUser.setRoleId(1);
                
                userRepository.save(newUser);
                
                userDetailsImpl = UserDetailsImpl.build(newUser);
            }

            if (userDetailsImpl == null || userDetailsImpl.getEmail() == null || userDetailsImpl.getEmail().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(JsonResponse.error("카카오 로그인 실패: 이메일 정보를 가져오지 못했습니다."));
            }
            
            OAuth2User oAuth2User = userDetailsImpl;
            Authentication authentication = new OAuth2AuthenticationToken(
                oAuth2User, 
                Collections.singletonList(new SimpleGrantedAuthority(ERole.fromCode(userDetailsImpl.getRoleId()).name())),
                userDetailsImpl.getProviderId()
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetailsImpl.getId());
            
            ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                    .body(JsonResponse.success("로그인 성공"));
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(JsonResponse.error("서버 오류: " + e.getMessage()));
        }   
    }
}
