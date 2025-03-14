package com.example.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.entity.TokenEntity;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
  List<TokenEntity> findAllValidIsFalseOrRevokedIsFalseByUserId(Long id);

  Optional<TokenEntity> findByToken(String token);
}
