package com.codemages.moviee.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codemages.moviee.dtos.UserCreateDTO;
import com.codemages.moviee.dtos.UserResponseDTO;
import com.codemages.moviee.entities.DocumentType;
import com.codemages.moviee.entities.Role;
import com.codemages.moviee.entities.User;
import com.codemages.moviee.exceptions.DuplicateUserException;
import com.codemages.moviee.repositories.UserRepository;
import com.codemages.moviee.utils.validators.Validator;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	@Qualifier("passwordValidator")
	private final Validator<String> passwordValidator;

	public UserService(
			UserRepository userRepository,
			PasswordEncoder passwordEncoder,
			@Qualifier("passwordValidator") Validator<String> passwordValidator) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.passwordValidator = passwordValidator;
	}

	public UserResponseDTO createUser(UserCreateDTO userCreateDto) {
		validateUser(userCreateDto);

		return toUserResponseDTO(
				userRepository.save(createUserEntity(userCreateDto)));
	}

	private void validateUser(UserCreateDTO userCreateDto) {
		if (!passwordValidator.isValid(userCreateDto.password())) {
			throw new IllegalArgumentException("Password is not valid");
		}

		User u = userRepository.findOptionalByEmailOrUsername(
				userCreateDto.email(), userCreateDto.username()).orElse(null);

		if (u != null) {
			if (u.getEmail().equals(userCreateDto.email())) {
				throw new DuplicateUserException("Email already registered");
			}

			if (u.getUsername().equals(userCreateDto.username())) {
				throw new DuplicateUserException("Username already registered");
			}
		}
	}

	private User createUserEntity(UserCreateDTO userCreateDto) {
		return new User(
				null,
				userCreateDto.username(),
				userCreateDto.email(),
				passwordEncoder.encode(userCreateDto.password()),
				Role.valueOf(userCreateDto.role()),
				userCreateDto.document(),
				DocumentType.fromString(userCreateDto.documentType()));
	}

	public List<UserResponseDTO> findAll() {
		return userRepository.findAll().stream().map(this::toUserResponseDTO)
				.toList();
	}

	public UserResponseDTO findById(UUID id) {
		return toUserResponseDTO(userRepository.findById(id).orElse(null));
	}

	private UserResponseDTO toUserResponseDTO(User u) {
		return u == null ? null
				: new UserResponseDTO(
						u.getId(),
						u.getUsername(),
						u.getEmail(),
						u.getRole().name(),
						u.getStatus().toString());
	}

	public long count() {
		return userRepository.count();
	}
}
