package com.gt.scr.utils;

import com.gt.scr.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class JwtTokenUtil {
    private final Key signingKey;
    private final String token;

    public JwtTokenUtil(String jwtToken, Key signingKey) {
        this.token = jwtToken;
        this.signingKey = signingKey;
    }

    public String getUsernameFromToken() {
        return getClaimFromToken(Claims::getSubject);
    }

    public Date getExpirationDateFromToken() {
        return getClaimFromToken(Claims::getExpiration);
    }

    public <T> T getClaimFromToken(Function<Claims, T> claimsResolver) {
        final var claims = getAllClaimsFromToken();
        return claimsResolver.apply(claims);
    }

    public Boolean validateToken(User userDetails) {
        final String username = getUsernameFromToken();
        return (username != null && username.equals(userDetails.username()));
    }

    private Claims getAllClaimsFromToken() {
        return Jwts.parserBuilder().setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token).getBody();
    }

    private static String doGenerateToken(Map<String, Object> claims, String subject, UUID id,
                                          Duration tokenDuration,
                                          Key signingKey) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setId(id.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenDuration.toMillis()))
                .signWith(signingKey)
                .compact();
    }

    private static Map<String, Object> addClaimsV2(Collection<String> authorities) {
        return Map.of("Authorities", authorities);
    }

    public static String generateTokenV2(User user,
                                         Duration tokenDuration, Key signingKey) {
        Map<String, Object> claims = addClaimsV2(user.authorities());
        return doGenerateToken(claims, user.username(), user.id(), tokenDuration, signingKey);
    }

    public String getUserIdFromToken() {
        return getClaimFromToken(Claims::getId);
    }

    public boolean isTokenValid() {
        try {
            getUsernameFromToken();
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}