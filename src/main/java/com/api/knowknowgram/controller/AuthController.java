package com.api.knowknowgram.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.knowknowgram.common.annotation.CommonApiResponses;
import com.api.knowknowgram.common.exception.TokenRefreshException;
import com.api.knowknowgram.common.response.JsonResponse;
import com.api.knowknowgram.common.security.JwtUtils;
import com.api.knowknowgram.common.security.UserDetailsImpl;
import com.api.knowknowgram.common.util.Helper;
import com.api.knowknowgram.common.util.LogType;
import com.api.knowknowgram.entity.RefreshToken;
import com.api.knowknowgram.payload.request.LoginRequest;
import com.api.knowknowgram.repository.UserRepository;
import com.api.knowknowgram.service.AuthService;
import com.api.knowknowgram.service.RefreshTokenService;
import com.api.knowknowgram.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
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

    @PostMapping("/signin")
    @Operation(summary = "로그인", description = "")
    @CommonApiResponses
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        
        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(JsonResponse.success());
    }

    @GetMapping("/test/kakao")
    @Operation(summary = "인가코드 테스트", description = "")
    @CommonApiResponses
    public ResponseEntity<?> test(@RequestParam String code) {
        return authService.oauthLogin(code);
    }

    @GetMapping("/kakao/signin")
    @Operation(summary = "kakao signin", description = "")
    @CommonApiResponses
    public ResponseEntity<?> kakaoSignin(@RequestParam String code) {
        // authService.kakaoLogin(code);

        return ResponseEntity.ok()                
                .body(JsonResponse.success());        
    }

    @PostMapping("/refreshtoken")
    @Operation(summary = "토큰 재발행", description = "")
    @CommonApiResponses
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);
        
        if ((refreshToken != null) && (refreshToken.length() > 0)) {
            return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    UserDetailsImpl userDetailsImpl = UserDetailsImpl.build(user);
                    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetailsImpl);
                    
                    return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                        .body(JsonResponse.success());
                })
                .orElseThrow(() -> new TokenRefreshException(refreshToken,
                    "Refresh token is not in database!"));
        }
        
        return ResponseEntity.badRequest().body(JsonResponse.error("Refresh Token is empty!"));        
    }
}