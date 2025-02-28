package com.example.library.entity;

import java.util.Date;

import com.example.library.enums.LoanStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loans")
public class LoanEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "start_date", nullable = false)
  private Date startDate;

  @Column(name = "return_date", nullable = true)
  private Date returnDate;

  @Enumerated(EnumType.STRING)
  private LoanStatusEnum status;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @OneToOne
  @JoinColumn(name = "book_id")
  private BookEntity book;
}
