package com.codemages.Moviee.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "user_tb")
@Data
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Column(nullable = false, unique = true, length = 20)
  private String username;
  @Column(nullable = false, unique = true)
  private String email;
  @Column(nullable = false)
  private String password;
  @Column(nullable = true, unique = true, length = 20)
  private String document;

  @Enumerated(EnumType.STRING)
  @Column(nullable = true, length = 10)
  private DocumentType documentType;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 25)
  private Role role;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 25)
  private UserStatus status;

  public User() {}

  public User(
    UUID id, String username, String email, String password,
    Role role, String document, DocumentType documentType
  ) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
    this.status = UserStatus.ACTIVE;
    this.document = document;
    this.documentType = documentType;
  }
}
