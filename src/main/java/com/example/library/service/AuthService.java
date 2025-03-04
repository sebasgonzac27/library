package com.example.library.service;

import com.example.library.dto.SignInRequestDto;
import com.example.library.dto.SignUpRequestDto;
import com.example.library.dto.TokenResponseDto;

public interface AuthService {
  TokenResponseDto signUp(SignUpRequestDto signUpRequestDto);

  TokenResponseDto signIn(SignInRequestDto signInRequestDto);

  TokenResponseDto refresh(String authHeader);

}
