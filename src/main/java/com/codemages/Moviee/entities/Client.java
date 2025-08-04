package com.codemages.Moviee.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String clientId;
  private String redirectUri;
}
