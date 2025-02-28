package com.codemages.moviee.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codemages.moviee.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(String name);
}
