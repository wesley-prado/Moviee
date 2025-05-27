package com.codemages.moviee.controllers.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemages.moviee.assemblers.UserModelAssembler;
import com.codemages.moviee.dtos.RestResponse;
import com.codemages.moviee.dtos.UserCreateDTO;
import com.codemages.moviee.dtos.UserResponseDTO;
import com.codemages.moviee.entities.Role;
import com.codemages.moviee.security.AuthContextHelper;
import com.codemages.moviee.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
	private static final String RESPONSE_TYPE = "application/hal+json";
	private static final MediaType DEFAULT_MEDIA_TYPE = MediaType
			.parseMediaType(RESPONSE_TYPE);

	@Autowired
	private UserModelAssembler userModelAssembler;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthContextHelper authContextHelper;

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(produces = RESPONSE_TYPE)
	public ResponseEntity<CollectionModel<EntityModel<UserResponseDTO>>> getUsers() {

		return ResponseEntity.ok().contentType(DEFAULT_MEDIA_TYPE)
				.body(userModelAssembler.toCollectionModel(userService.findAll()));
	}

	@GetMapping(value = "/{id}", produces = RESPONSE_TYPE)
	public ResponseEntity<EntityModel<UserResponseDTO>> getUser(
			@PathVariable UUID id) {
		UserResponseDTO dto = userService.findById(id);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok().contentType(DEFAULT_MEDIA_TYPE)
				.body(userModelAssembler.toModel(dto));
	}

	@PostMapping(consumes = "application/json", produces = RESPONSE_TYPE)
	public ResponseEntity<EntityModel<RestResponse<UserResponseDTO>>> createUser(
			@RequestBody @Valid UserCreateDTO dto) {

		if (dto.role().equalsIgnoreCase(Role.ADMIN.name())
				&& !authContextHelper.isUserAdmin()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.contentType(DEFAULT_MEDIA_TYPE)
					.body(EntityModel.of(
							new RestResponse<>(
									"You do not have permission to create an admin user.")));
		}

		UserResponseDTO result = userService.createUser(dto);

		if (result == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(DEFAULT_MEDIA_TYPE)
					.body(EntityModel.of(new RestResponse<>(
							"User creation failed. Please check the input data.")));
		}

		return ResponseEntity.status(HttpStatus.CREATED)
				.contentType(DEFAULT_MEDIA_TYPE)
				.body(EntityModel
						.of(new RestResponse<>(result, "User created successfully.")));
	}
}
