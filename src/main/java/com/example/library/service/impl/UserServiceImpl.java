package com.example.library.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.library.dto.UserDto;
import com.example.library.entity.UserEntity;
import com.example.library.exception.ResourceAlreadyExistsException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.mapper.UserMapper;
import com.example.library.repository.UserRepository;
import com.example.library.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public Page<UserDto> findAll(Pageable pageable) {
    Page<UserEntity> page = userRepository.findAll(pageable);
    return page.map(userEntity -> userMapper.toDto(userEntity));
  }

  @Override
  public UserEntity findOne(Long id) throws ResourceNotFoundException {
    return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  @Override
  public UserEntity findOne(String email) throws ResourceNotFoundException {
    return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  @Override
  public UserDto create(UserEntity userEntity) throws ResourceAlreadyExistsException {

    if (userRepository.existsByEmail(userEntity.getEmail())) {
      throw new ResourceAlreadyExistsException("User already exists");
    }

    UserEntity newUser = userRepository.save(userEntity);
    UserDto newUserDto = userMapper.toDto(newUser);
    return newUserDto;
  }

  @Override
  public UserDto update(Long id, UserEntity userEntity) throws ResourceNotFoundException {
    if (!userRepository.existsById(id)) {
      throw new ResourceNotFoundException("User not found");
    }

    UserEntity userToUpdate = userEntity;
    userToUpdate.setId(id);
    UserEntity updatedUser = userRepository.save(userToUpdate);
    UserDto userDto = userMapper.toDto(updatedUser);
    return userDto;
  }

  @Override
  public void delete(Long id) throws ResourceNotFoundException {
    if (!userRepository.existsById(id)) {
      throw new ResourceNotFoundException("User not found");
    }
    userRepository.deleteById(id);
  }
}
