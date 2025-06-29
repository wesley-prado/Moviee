package com.codemages.Moviee.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.codemages.Moviee.controllers.v1.UserController;
import com.codemages.Moviee.dtos.UserResponseDTO;

import lombok.NonNull;

@Component
public class UserModelAssembler implements
		RepresentationModelAssembler<UserResponseDTO, EntityModel<UserResponseDTO>> {

	@NonNull
	@Override
	public EntityModel<UserResponseDTO> toModel(
			UserResponseDTO response) {
		Link selfLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UserController.class)
						.getUser(response.id()))
				.withSelfRel();
		Link usersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(UserController.class).getUsers())
				.withRel("users");

		return EntityModel.of(response, selfLink, usersLink);
	}
}
