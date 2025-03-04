package com.example.library.dto;

import lombok.Data;

@Data
public class SignInRequestDto {
  private String email;
  private String password;
}
