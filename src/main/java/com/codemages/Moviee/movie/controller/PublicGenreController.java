package com.codemages.Moviee.movie.controller;

import com.codemages.Moviee.constant.ControllerConstants;
import com.codemages.Moviee.movie.GenreService;
import com.codemages.Moviee.movie.assembler.GenreModelAssembler;
import com.codemages.Moviee.movie.constant.Genre;
import com.codemages.Moviee.movie.dto.GenreResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerConstants.PUBLIC_API_BASE + "/v1/genres")
@AllArgsConstructor
public class PublicGenreController {
  private final GenreService genreService;
  private final GenreModelAssembler genreModelAssembler = new GenreModelAssembler();

  @GetMapping
  public ResponseEntity<CollectionModel<EntityModel<GenreResponseDTO>>> getGenres() {
    return ResponseEntity.ok()
      .contentType( MediaTypes.HAL_JSON )
      .body( genreModelAssembler.toCollectionModel(
        genreService.convertToGenreResponseDTO( Genre.values() )
      ) );
  }
}
