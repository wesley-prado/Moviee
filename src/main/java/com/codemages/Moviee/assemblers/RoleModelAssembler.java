package com.codemages.moviee.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.codemages.moviee.controllers.v1.RoleController;
import com.codemages.moviee.dtos.RoleResponseDTO;

import lombok.NonNull;

@NonNull
@Component
public class RoleModelAssembler implements
		RepresentationModelAssembler<RoleResponseDTO, EntityModel<RoleResponseDTO>> {

	@Override
	public EntityModel<RoleResponseDTO> toModel(
			RoleResponseDTO response) {
		Link rolesLink = WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(RoleController.class).getRoles())
				.withRel("roles");

		return EntityModel.of(response, rolesLink);
	}
}
