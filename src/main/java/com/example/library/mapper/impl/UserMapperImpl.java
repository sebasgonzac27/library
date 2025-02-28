package com.example.library.mapper.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.example.library.dto.SignUpRequestDto;
import com.example.library.dto.UserDto;
import com.example.library.entity.UserEntity;
import com.example.library.mapper.UserMapper;

@Component
public class UserMapperImpl implements UserMapper {

  @Override
  public UserDto toDto(UserEntity userEntity) {
    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(userEntity, userDto);
    return userDto;
  }

  @Override
  public UserEntity toEntity(SignUpRequestDto signUpRequestDto) {
    UserEntity userEntity = new UserEntity();
    BeanUtils.copyProperties(signUpRequestDto, userEntity);
    return userEntity;
  }

  @Override
  public UserEntity toEntity(UserDto userDto) {
    UserEntity userEntity = new UserEntity();
    BeanUtils.copyProperties(userDto, userEntity);
    return userEntity;
  }

}
