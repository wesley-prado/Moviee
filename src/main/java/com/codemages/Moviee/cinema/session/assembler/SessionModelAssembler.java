package com.codemages.Moviee.cinema.session.assembler;

import com.codemages.Moviee.cinema.session.controller.v1.PublicSessionController;
import com.codemages.Moviee.cinema.session.dto.SessionResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class SessionModelAssembler
  implements RepresentationModelAssembler<SessionResponseDTO, EntityModel<SessionResponseDTO>> {
  @Override
  public EntityModel<SessionResponseDTO> toModel(SessionResponseDTO dto) {
    Link selfLink =
      WebMvcLinkBuilder.linkTo( WebMvcLinkBuilder.methodOn( PublicSessionController.class )
        .findById( dto.id() ) ).withSelfRel();

    return EntityModel.of( dto, selfLink );
  }
}
