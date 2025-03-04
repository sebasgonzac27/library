package com.example.library.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.dto.SignInRequestDto;
import com.example.library.dto.SignUpRequestDto;
import com.example.library.dto.TokenResponseDto;
import com.example.library.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private AuthService authService;

  @PostMapping("/sign-in")
  private ResponseEntity<TokenResponseDto> signIn(@RequestBody SignInRequestDto signInRequestDto) {
    final TokenResponseDto token = authService.signIn(signInRequestDto);
    return ResponseEntity.ok(token);
  }

  @PostMapping("/sign-up")
  private ResponseEntity<TokenResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
    final TokenResponseDto token = authService.signUp(signUpRequestDto);
    return ResponseEntity.ok(token);
  }

  @PostMapping("/refresh")
  public ResponseEntity<TokenResponseDto> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
    final TokenResponseDto token = authService.refresh(authHeader);
    return ResponseEntity.ok(token);
  }
}
