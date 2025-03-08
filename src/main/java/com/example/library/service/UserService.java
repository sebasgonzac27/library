package com.example.library.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.library.dto.UserDto;
import com.example.library.entity.UserEntity;
import com.example.library.exception.ResourceAlreadyExistsException;
import com.example.library.exception.ResourceNotFoundException;

public interface UserService {
  Page<UserDto> findAll(Pageable pageable);

  UserEntity findOne(Long id) throws ResourceNotFoundException;

  UserEntity findOne(String email) throws ResourceNotFoundException;

  UserDto create(UserEntity userEntity) throws ResourceAlreadyExistsException;

  UserDto update(Long id, UserEntity userEntity) throws ResourceNotFoundException;

  void delete(Long id) throws ResourceNotFoundException;
}
