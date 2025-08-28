package com.codemages.Moviee.movie.assembler;

import com.codemages.Moviee.movie.controller.PublicGenreController;
import com.codemages.Moviee.movie.controller.PublicMovieController;
import com.codemages.Moviee.movie.dto.MovieResponseDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class MovieModelAssembler
  implements RepresentationModelAssembler<MovieResponseDTO, EntityModel<MovieResponseDTO>> {
  @Override
  public @NotNull EntityModel<MovieResponseDTO> toModel(@NotNull MovieResponseDTO entity) {
    Link moviesLink = WebMvcLinkBuilder.linkTo(
      WebMvcLinkBuilder.methodOn( PublicMovieController.class ).getAllMovies()
    ).withRel( "movies" );
    Link genresLink = WebMvcLinkBuilder.linkTo(
      WebMvcLinkBuilder.methodOn( PublicGenreController.class ).getGenres()
    ).withRel( "genres" );

    return EntityModel.of( entity, moviesLink, genresLink );
  }
}
