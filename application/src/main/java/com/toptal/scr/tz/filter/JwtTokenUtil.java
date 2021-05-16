package com.toptal.scr.tz.filter;

import com.toptal.scr.tz.service.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtTokenUtil {
    private static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 1000; //TODO Load from Config
    private static final String SECRET = "19CA249C582715657BDCAB1FB31E69F854443A4FE3CBAFFD215E3F3676"; //TODO Load from config
    private static final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
    private static final Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

    private String token = null;

    public JwtTokenUtil(String jwtToken) {
        this.token = jwtToken;
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

    public static String generateToken(User user) {
        Map<String, Object> claims = addClaims(user);
        return doGenerateToken(claims, user.getUsername());
    }

    private static Map<String, Object> addClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("Authorities", user.getAuthorities());
        return claims;
    }

    private static String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(signingKey)
                .compact();
    }

    public Boolean validateToken(UserDetails userDetails) {
        final String username = getUsernameFromToken();
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired());
    }
}