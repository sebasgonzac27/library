package com.example.library.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class TokenResponseDto {
  private String access_token;
  private String refresh_token;
}
