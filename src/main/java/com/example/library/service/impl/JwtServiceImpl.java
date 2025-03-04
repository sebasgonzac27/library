package com.example.library.service.impl;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.library.entity.UserEntity;
import com.example.library.service.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

  @Value("${security.jwt.secret-key}")
  private String secretKey;

  @Value("${security.jwt.expiration}")
  private Long expiration;

  @Value("${security.jwt.refresh-token.expiration}")
  private Long refreshExpiration;

  @Override
  public String generateToken(UserEntity user) {
    return buildToken(user, expiration);
  }

  @Override
  public String generateRefreshToken(UserEntity user) {
    return buildToken(user, refreshExpiration);
  }

  private String buildToken(UserEntity user, Long expiration) {
    return Jwts.builder()
        .id(user.getId().toString())
        .subject(user.getEmail())
        .claim("name", user.getName())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignKey())
        .compact();
  }

  private SecretKey getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
