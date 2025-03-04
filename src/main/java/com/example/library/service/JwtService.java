package com.example.library.service;

import com.example.library.entity.UserEntity;

public interface JwtService {
  String generateToken(UserEntity user);

  String generateRefreshToken(UserEntity user);

}
