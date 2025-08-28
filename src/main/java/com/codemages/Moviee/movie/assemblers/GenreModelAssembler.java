package com.codemages.Moviee.movie.assemblers;

import com.codemages.Moviee.movie.dto.GenreResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public class GenreModelAssembler
  implements RepresentationModelAssembler<GenreResponseDTO, EntityModel<GenreResponseDTO>> {

  @Override
  public EntityModel<GenreResponseDTO> toModel(GenreResponseDTO entity) {
    return EntityModel.of( entity );
  }
}
