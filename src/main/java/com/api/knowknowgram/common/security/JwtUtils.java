package com.api.knowknowgram.common.security;

import java.security.Key;
import java.util.Date;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.api.knowknowgram.common.response.ResponseCode;
import com.api.knowknowgram.common.util.Helper;
import com.api.knowknowgram.common.util.LogType;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils { 
    private String jwtSecret = System.getenv("JWT_SECRET");
    private Long jwtExpirationMs = 3600000L;
    private String jwtCookie = System.getenv("JWT_COOKIE_NAME");
    private String jwtRefreshCookie = System.getenv("JWT_REFRESH_COOKIE_NAME");

    public ResponseCookie generateJwtCookie(UserDetailsImpl user) {
        String jwt = generateTokenFromEmail(user.getEmail());   

        return generateCookie(jwtCookie, jwt, "/api");
    }
    
    public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
        return generateCookie(jwtRefreshCookie, refreshToken, "/api/auth/refreshtoken");
    }
    
    public String getJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtCookie);
    }
    
    public String getJwtRefreshFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtRefreshCookie);
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();

        return cookie;
    }
    
    public ResponseCookie getCleanJwtRefreshCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtRefreshCookie, null).path("/api/auth/refreshtoken").build();

        return cookie;
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
            .parseClaimsJws(token).getBody().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);

            return true;
        } catch (MalformedJwtException e) {
            Helper.apiLog(LogType.ERROR, ResponseCode.INVALID_JWT_TOKEN.getMessage());            
        } catch (ExpiredJwtException e) {
            Helper.apiLog(LogType.ERROR, ResponseCode.EXPIRED_JWT_TOKEN.getMessage());            
        } catch (UnsupportedJwtException e) {
            Helper.apiLog(LogType.ERROR, ResponseCode.UNSUPPORTED_JWT_TOKEN.getMessage());            
        } catch (IllegalArgumentException e) {
            Helper.apiLog(LogType.ERROR, ResponseCode.EMPTY_JWT_CLAIMS.getMessage());            
        }

        return false;
    }
    
    public String generateTokenFromEmail(String email) {   
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact();
    }
        
    private ResponseCookie generateCookie(String name, String value, String path) {
        ResponseCookie cookie = ResponseCookie.from(name, value).path(path).maxAge(24 * 60 * 60).httpOnly(true).build();

        return cookie;
    }
    
    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);

        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }
}