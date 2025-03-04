package com.example.library.service.impl;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.library.dto.SignInRequestDto;
import com.example.library.dto.SignUpRequestDto;
import com.example.library.dto.TokenResponseDto;
import com.example.library.entity.TokenEntity;
import com.example.library.entity.UserEntity;
import com.example.library.enums.TokenType;
import com.example.library.repository.TokenRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.AuthService;
import com.example.library.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private UserRepository userRepository;
  private TokenRepository tokenRepository;
  private PasswordEncoder passwordEncoder;
  private JwtService jwtService;
  private AuthenticationManager authenticationManager;

  @Override
  public TokenResponseDto signIn(SignInRequestDto signInRequestDto) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            signInRequestDto.getEmail(),
            signInRequestDto.getPassword()));

    UserEntity user = userRepository.findByEmail(signInRequestDto.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    String jwtToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    return new TokenResponseDto(jwtToken, refreshToken);
  }

  @Override
  public TokenResponseDto signUp(SignUpRequestDto signUpRequestDto) {
    UserEntity user = UserEntity.builder()
        .name(signUpRequestDto.getName())
        .email(signUpRequestDto.getEmail())
        .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
        .build();

    UserEntity savedUser = userRepository.save(user);
    String token = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);
    saveToken(savedUser, token);
    return new TokenResponseDto(token, refreshToken);
  }

  @Override
  public TokenResponseDto refresh(String authHeader) {
    return null;
  }

  private void saveToken(UserEntity user, String token) {
    TokenEntity jwtToken = TokenEntity.builder()
        .user(user)
        .token(token)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();

    tokenRepository.save(jwtToken);
  }

  private void revokeAllUserTokens(UserEntity user) {
    List<TokenEntity> tokens = tokenRepository.findAllValidIsFalseOrRevokedIsFalseByUserId(user.getId());
    if (!tokens.isEmpty()) {
      for (final TokenEntity token : tokens) {
        token.setExpired(true);
        token.setRevoked(true);
      }
      tokenRepository.saveAll(tokens);
    }
  }

}
