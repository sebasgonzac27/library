package com.example.library.dto;

import lombok.Data;

@Data
public class ValidationErrorDto {
  private String code;
  private String message;
}
