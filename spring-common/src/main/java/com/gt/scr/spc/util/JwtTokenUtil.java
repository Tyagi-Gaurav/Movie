package com.gt.scr.spc.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

    private Claims getAllClaimsFromToken() {
        return Jwts.parserBuilder().setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token).getBody();
    }

    public static String generateToken(String userName,
                                       UUID userId,
                                       Collection<GrantedAuthority> authorities,
                                       Duration tokenDuration, Key signingKey) {
        Map<String, Object> claims = addClaims(authorities);
        return doGenerateToken(claims, userName, userId, tokenDuration, signingKey);
    }

    private static Map<String, Object> addClaims(Collection<GrantedAuthority> authorities) {
        return Map.of("Authorities", authorities);
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
        return (username != null && username.equals(userDetails.getUsername()));
    }

    public String getUserIdFromToken() {
        return getClaimFromToken(Claims::getId);
    }
}