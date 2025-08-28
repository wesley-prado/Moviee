package com.codemages.Moviee.movie.dto;

import lombok.Builder;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Builder
@Relation(value = "movie", collectionRelation = "movies")
public record MovieResponseDTO(
  Long id,
  String title,
  Integer year,
  List<GenreResponseDTO> genres,
  String description
) {
}
