package com.codemages.Moviee.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String clientId;
	private String clientSecret;
	private String redirectUri;
}
