package com.example.library.dto;

import lombok.Data;

@Data
public class SignInResponseDto {
  private String email;
  private String token;
}
