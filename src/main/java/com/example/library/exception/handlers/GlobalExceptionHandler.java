package com.example.library.exception.handlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.library.dto.ErrorResponseDto;
import com.example.library.dto.ValidationErrorDto;
import com.example.library.exception.NotFoundException;
import com.example.library.exception.ResourceAlreadyExistsException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  @Autowired
  private MessageSource messageSource;

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException exception,
      HttpServletRequest request) {

    ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
        .timestamp(new Date().getTime())
        .status(HttpStatus.NOT_FOUND.value())
        .title("Resource not found")
        .detail(exception.getMessage())
        .developerMessage(exception.getClass().getName())
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
  }

  @ExceptionHandler(ResourceAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDto> handleResourceAlreadyExistsException(ResourceAlreadyExistsException exception,
      HttpServletRequest request) {

    ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
        .timestamp(new Date().getTime())
        .status(HttpStatus.CONFLICT.value())
        .title("Resource already exists")
        .detail(exception.getMessage())
        .developerMessage(exception.getClass().getName())
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDto);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception,
      HttpServletRequest request) {

    ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
        .timestamp(new Date().getTime())
        .status(HttpStatus.BAD_REQUEST.value())
        .title("Validation failed")
        .detail(exception.getMessage())
        .developerMessage(exception.getClass().getName())
        .path(request.getRequestURI())
        .build();

    List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
    for (FieldError fieldError : fieldErrors) {
      List<ValidationErrorDto> validationErrorsDto = errorResponseDto.getErrors().get(fieldError.getField());
      if (validationErrorsDto == null) {
        validationErrorsDto = new ArrayList<ValidationErrorDto>();
        errorResponseDto.getErrors().put(fieldError.getField(), validationErrorsDto);
      }

      ValidationErrorDto validationErrorDto = new ValidationErrorDto();
      validationErrorDto.setCode(fieldError.getCode());
      validationErrorDto.setMessage(messageSource.getMessage(fieldError, request.getLocale()));
      validationErrorsDto.add(validationErrorDto);
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
  }

}
