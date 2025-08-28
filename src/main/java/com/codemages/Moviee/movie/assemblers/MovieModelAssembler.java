package com.codemages.Moviee.movie.assemblers;

import com.codemages.Moviee.movie.controllers.PublicGenreController;
import com.codemages.Moviee.movie.controllers.PublicMovieController;
import com.codemages.Moviee.movie.dto.MovieResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class MovieModelAssembler
  implements RepresentationModelAssembler<MovieResponseDTO, EntityModel<MovieResponseDTO>> {
  @Override
  public EntityModel<MovieResponseDTO> toModel(MovieResponseDTO entity) {
    Link moviesLink = WebMvcLinkBuilder.linkTo(
      WebMvcLinkBuilder.methodOn( PublicMovieController.class ).getMovies()
    ).withRel( "movies" );
    Link genresLink = WebMvcLinkBuilder.linkTo(
      WebMvcLinkBuilder.methodOn( PublicGenreController.class ).getGenres()
    ).withRel( "genres" );

    return EntityModel.of( entity, moviesLink, genresLink );
  }
}
