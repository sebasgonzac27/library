package com.example.library.mapper;

import com.example.library.dto.SignUpRequestDto;
import com.example.library.dto.UserDto;
import com.example.library.entity.UserEntity;

public interface UserMapper {
  UserEntity toEntity(SignUpRequestDto signUpRequestDto);

  UserEntity toEntity(UserDto userDto);

  UserDto toDto(UserEntity userEntity);
}
