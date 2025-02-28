package com.example.library.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
  private String title;
  private int status;
  private String detail;
  private long timestamp;
  private String developerMessage;
  private String path;
  private Map<String, List<ValidationErrorDto>> errors;

}
