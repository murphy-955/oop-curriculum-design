package cn.edu.fzu.oopdesign.zeyuli.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    @Value("${jwt.token.tokenSignKey}")
    private String tokenSignKey;

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(tokenSignKey.getBytes());
    }

    public String generateToken(String gameId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("gameId", gameId);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(gameId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSignKey())
                .compact();
    }

    /**
     * 校验token是否有效，并判断是否过期
     *
     * @author : 李泽聿
     * @since : 2025-12-23 15:56
     * @param token : 传入token
     * @return : boolean
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getGameIdFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}