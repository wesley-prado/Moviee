package com.codemages.Moviee.controllers.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemages.Moviee.assemblers.RoleModelAssembler;
import com.codemages.Moviee.config.MediaTypes;
import com.codemages.Moviee.dtos.RoleResponseDTO;
import com.codemages.Moviee.entities.Role;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
	@Autowired
	private RoleModelAssembler roleModelAssembler;

	@GetMapping(produces = MediaTypes.RESPONSE_TYPE)
	public ResponseEntity<CollectionModel<EntityModel<RoleResponseDTO>>> getRoles() {
		List<RoleResponseDTO> roles = Arrays.stream(Role.values())
				.map(Role::name)
				.map(RoleResponseDTO::new)
				.toList();

		return ResponseEntity.ok().contentType(MediaTypes.DEFAULT_MEDIA_TYPE)
				.body(roleModelAssembler
						.toCollectionModel(roles));
	}
}
