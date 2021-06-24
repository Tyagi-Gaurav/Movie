package com.gt.scr.movie.filter;

import com.gt.scr.movie.service.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class JwtTokenUtil {
    private final Key signingKey;
    private String token;

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
        final Claims claims = getAllClaimsFromToken();
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken() {
        return Jwts.parserBuilder().setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired() {
        final Date expiration = getExpirationDateFromToken();
        return expiration.before(new Date());
    }

    public static String generateToken(User user, Duration tokenDuration, Key signingKey) {
        Map<String, Object> claims = addClaims(user);
        return doGenerateToken(claims, user.getUsername(), user.id(), tokenDuration, signingKey);
    }

    private static Map<String, Object> addClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("Authorities", user.getAuthorities());
        return claims;
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

    public Boolean validateToken(UserDetails userDetails) {
        final String username = getUsernameFromToken();
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired());
    }

    public String getUserIdFromToken() {
        return getClaimFromToken(Claims::getId);
    }
}