package com.codemages.Moviee.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "client_tb")
@Data
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(unique = true, nullable = false, length = 100)
  private UUID id;

  @Column(nullable = false, unique = true, length = 100)
  private String clientId;
  @Column(nullable = false, length = 255)
  private String redirectUri;
  @Column(nullable = false, length = 100)
  private String clientName;
}
