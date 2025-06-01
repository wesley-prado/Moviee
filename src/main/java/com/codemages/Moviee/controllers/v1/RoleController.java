package com.codemages.moviee.controllers.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemages.moviee.assemblers.RoleModelAssembler;
import com.codemages.moviee.dtos.RoleResponseDTO;
import com.codemages.moviee.entities.Role;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
	private static final String RESPONSE_TYPE = "application/hal+json";
	private static final MediaType DEFAULT_MEDIA_TYPE = MediaType
			.parseMediaType(RESPONSE_TYPE);

	@Autowired
	private RoleModelAssembler roleModelAssembler;

	@GetMapping(produces = RESPONSE_TYPE)
	public ResponseEntity<CollectionModel<EntityModel<RoleResponseDTO>>> getRoles() {
		List<RoleResponseDTO> roles = Arrays.stream(Role.values())
				.map(Role::name)
				.map(RoleResponseDTO::new)
				.toList();

		return ResponseEntity.ok().contentType(DEFAULT_MEDIA_TYPE)
				.body(roleModelAssembler
						.toCollectionModel(roles));
	}
}
