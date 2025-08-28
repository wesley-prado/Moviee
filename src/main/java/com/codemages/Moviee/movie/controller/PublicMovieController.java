package com.codemages.Moviee.movie.controller;

import com.codemages.Moviee.constant.ControllerConstant;
import com.codemages.Moviee.movie.MovieService;
import com.codemages.Moviee.movie.assembler.MovieModelAssembler;
import com.codemages.Moviee.movie.dto.MovieResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ControllerConstant.PUBLIC_API_BASE + "/v1/movies")
@RequiredArgsConstructor
public class PublicMovieController {
  private final MovieService movieService;
  private static final MovieModelAssembler movieModelAssembler = new MovieModelAssembler();

  @GetMapping
  public ResponseEntity<CollectionModel<EntityModel<MovieResponseDTO>>> getAllMovies() {
    List<MovieResponseDTO> movies = movieService.findAll();

    return ResponseEntity.ok()
      .contentType( MediaTypes.HAL_JSON )
      .body( movieModelAssembler.toCollectionModel( movies ) );
  }
}
