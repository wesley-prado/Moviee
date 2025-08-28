package com.codemages.Moviee.cinema.movie.assembler;

import com.codemages.Moviee.cinema.movie.dto.GenreResponseDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public class GenreModelAssembler
  implements RepresentationModelAssembler<GenreResponseDTO, EntityModel<GenreResponseDTO>> {

  @Override
  public @NotNull EntityModel<GenreResponseDTO> toModel(@NotNull GenreResponseDTO entity) {
    return EntityModel.of( entity );
  }
}
