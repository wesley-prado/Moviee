package com.codemages.Moviee.user.assembler;

import com.codemages.Moviee.user.controller.v1.PrivateUserController;
import com.codemages.Moviee.user.dto.UserResponseDTO;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements
                                RepresentationModelAssembler<UserResponseDTO,
                                  EntityModel<UserResponseDTO>> {

  @NonNull
  @Override
  public EntityModel<UserResponseDTO> toModel(
    UserResponseDTO dto
  ) {
    Link selfLink = WebMvcLinkBuilder
      .linkTo( WebMvcLinkBuilder
        .methodOn( PrivateUserController.class )
        .getUser( dto.id() ) )
      .withSelfRel();
    Link usersLink = WebMvcLinkBuilder.linkTo( WebMvcLinkBuilder
        .methodOn( PrivateUserController.class ).getUsers() )
      .withRel( "users" );

    return EntityModel.of( dto, selfLink, usersLink );
  }
}
