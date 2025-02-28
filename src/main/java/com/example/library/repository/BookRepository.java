package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
