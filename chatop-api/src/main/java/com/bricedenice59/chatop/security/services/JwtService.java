package com.bricedenice59.chatop.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.expiration}")
    private long jwtExpirationDuration;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    
    public String generateToken(UserDetails userDetails) {
         return generateToken(new HashMap<>(), userDetails);

    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, jwtExpirationDuration);
    }

    private String buildToken(
            Map<String, Object> claims,
            UserDetails userDetails,
            long jwtExpirationDuration) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .toList();
        return Jwts
                .builder()
                .claims(claims)
                .claim("authorities", authorities)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationDuration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
         return userDetails.getUsername().equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
