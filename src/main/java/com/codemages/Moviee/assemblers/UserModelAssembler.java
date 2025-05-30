package com.codemages.moviee.assemblers;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.codemages.moviee.controllers.v1.UserController;
import com.codemages.moviee.dtos.RestResponse;
import com.codemages.moviee.dtos.UserResponseDTO;

import lombok.NonNull;

@Component
public class UserModelAssembler implements
		RepresentationModelAssembler<RestResponse<List<UserResponseDTO>>, EntityModel<RestResponse<List<UserResponseDTO>>>> {

	@NonNull
	@Override
	public EntityModel<RestResponse<List<UserResponseDTO>>> toModel(
			RestResponse<List<UserResponseDTO>> response) {
		Link selfLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UserController.class)
						.getUser(response.data().getFirst().id()))
				.withSelfRel();
		Link usersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(UserController.class).getUsers())
				.withRel("users");

		return EntityModel.of(response, selfLink, usersLink);
	}
}
