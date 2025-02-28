package com.example.library.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "books")
public class BookEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String title;

  @Column(nullable = false)
  private String genre;

  @Column(name = "publication_year", nullable = false)
  private Date publicationYear;

  @Column(name = "available_quantity", nullable = false)
  private int availableQuantity;

  @ManyToOne()
  @JoinColumn(name = "author_id")
  private AuthorEntity author;

  @OneToOne(mappedBy = "book")
  private LoanEntity loan;
}
