package com.codemages.moviee.assemblers;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.codemages.moviee.controllers.v1.RoleController;
import com.codemages.moviee.dtos.RestResponse;
import com.codemages.moviee.dtos.RoleResponseDTO;

import lombok.NonNull;

@NonNull
@Component
public class RoleModelAssembler implements
		RepresentationModelAssembler<RestResponse<List<RoleResponseDTO>>, EntityModel<RestResponse<List<RoleResponseDTO>>>> {

	@Override
	public EntityModel<RestResponse<List<RoleResponseDTO>>> toModel(
			RestResponse<List<RoleResponseDTO>> response) {
		Link rolesLink = WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(RoleController.class).getRoles())
				.withRel("roles");

		return EntityModel.of(response, rolesLink);
	}
}
