package com.codemages.Moviee.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.codemages.Moviee.entities.User;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByUsername(String username);

	Optional<User> findOptionalByEmailOrUsername(String email, String username);
}
