package com.codemages.moviee.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codemages.moviee.dtos.RoleResponseDTO;
import com.codemages.moviee.dtos.UserCreateDTO;
import com.codemages.moviee.dtos.UserResponseDTO;
import com.codemages.moviee.entities.DocumentType;
import com.codemages.moviee.entities.Role;
import com.codemages.moviee.entities.RoleStatus;
import com.codemages.moviee.entities.User;
import com.codemages.moviee.repositories.UserRepository;
import com.codemages.moviee.utils.validators.Validator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	@Autowired
	private final RoleService roleService;
	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final PasswordEncoder passwordEncoder;
	@Autowired
	@Qualifier("passwordValidator")
	private final Validator<String> passwordValidator;

	public UserResponseDTO save(UserCreateDTO userCreateDto) {
		if (!passwordValidator.isValid(userCreateDto.password())) {
			throw new IllegalArgumentException("Password is not valid");
		}

		RoleResponseDTO role = roleService.findByName(userCreateDto.role());

		User u = new User(
				null,
				userCreateDto.username(),
				userCreateDto.email(),
				passwordEncoder.encode(userCreateDto.password()),
				new Role(
						role.id(),
						role.name(),
						RoleStatus.ACTIVE),
				userCreateDto.document(),
				DocumentType.fromString(userCreateDto.documentType()));

		return toUserResponseDTO(userRepository.save(u));
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
						new RoleResponseDTO(
								u.getRole().getId(),
								u.getRole().getName()),
						u.getStatus().toString());
	}

	public long count() {
		return userRepository.count();
	}
}
