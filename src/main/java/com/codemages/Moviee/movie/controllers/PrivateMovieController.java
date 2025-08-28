package com.codemages.Moviee.movie.controllers;

import com.codemages.Moviee.constants.ControllerConstants;
import com.codemages.Moviee.movie.MovieService;
import com.codemages.Moviee.movie.assemblers.MovieModelAssembler;
import com.codemages.Moviee.movie.dto.MovieResponseDTO;
import com.codemages.Moviee.movie.dto.PrivateMovieCreationDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerConstants.API_BASE + "/v1/movies")
@RequiredArgsConstructor
public class PrivateMovieController {
  private final MovieService movieService;
  private final MovieModelAssembler movieModelAssembler =
    new MovieModelAssembler();

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ResponseEntity<EntityModel<MovieResponseDTO>> createMovie(
    @RequestBody @Valid
    PrivateMovieCreationDTO dto
  ) {
    MovieResponseDTO result = movieService.save( dto );

    return ResponseEntity.ok()
      .contentType( MediaTypes.HAL_JSON )
      .body( movieModelAssembler.toModel( result ) );
  }
}
