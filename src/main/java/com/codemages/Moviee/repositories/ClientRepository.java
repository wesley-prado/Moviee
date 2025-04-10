package com.codemages.moviee.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codemages.moviee.entities.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {
	Client findByClientId(String clientId);
}
