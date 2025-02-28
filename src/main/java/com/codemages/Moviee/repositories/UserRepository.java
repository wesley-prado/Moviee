package com.codemages.moviee.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codemages.moviee.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
