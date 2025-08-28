package com.codemages.Moviee.user.assemblers;

import com.codemages.Moviee.user.controllers.v1.PrivateUserController;
import com.codemages.Moviee.user.dto.UserResponseDTO;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements
		RepresentationModelAssembler<UserResponseDTO, EntityModel<UserResponseDTO>> {

	@NonNull
	@Override
	public EntityModel<UserResponseDTO> toModel(
			UserResponseDTO response) {
		Link selfLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
          .methodOn( PrivateUserController.class )
						.getUser(response.id()))
				.withSelfRel();
		Link usersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
        .methodOn( PrivateUserController.class ).getUsers() )
				.withRel("users");

		return EntityModel.of(response, selfLink, usersLink);
	}
}
