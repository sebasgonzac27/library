package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.entity.LoanEntity;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
}
