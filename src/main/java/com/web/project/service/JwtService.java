package com.web.project.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
<<<<<<< HEAD
import java.util.Base64;
import java.util.Date;
=======
import java.util.Date;
import java.util.Map;
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
import java.util.function.Function;

@Service
public class JwtService {

<<<<<<< HEAD
    private static final String SECRET_KEY = Base64.getEncoder().encodeToString("6A7D5F9EAF9F7C3F1C4B9A7E3D9E2F8A6C5E7B3C9D2A1F0E9B3D7C8A6E1F4D7B".getBytes());

    public String generateToken(UserDetails userDetails) {

        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("rol", userDetails.getAuthorities())
=======
    private static final String SECRET_KEY = "6A7D5F9EAF9F7C3F1C4B9A7E3D9E2F8A6C5E7B3C9D2A1F0E9B3D7C8A6E1F4D7B";

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

<<<<<<< HEAD
    // public String generateToken(UserDetails userDetails) {
    //     return generateToken(Map.of(), userDetails);
    // }
=======
    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of(), userDetails);
    }
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
