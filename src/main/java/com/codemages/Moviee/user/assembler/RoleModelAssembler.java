package com.codemages.Moviee.user.assembler;

import com.codemages.Moviee.user.controller.v1.PrivateRoleController;
import com.codemages.Moviee.user.dto.RoleResponseDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class RoleModelAssembler
  implements RepresentationModelAssembler<RoleResponseDTO, EntityModel<RoleResponseDTO>> {

  @Override
  public @NotNull EntityModel<RoleResponseDTO> toModel(
    @NotNull RoleResponseDTO response
  ) {
    Link rolesLink =
      WebMvcLinkBuilder.linkTo( WebMvcLinkBuilder.methodOn( PrivateRoleController.class )
      .getRoles() ).withRel( "roles" );

    return EntityModel.of( response, rolesLink );
  }
}
