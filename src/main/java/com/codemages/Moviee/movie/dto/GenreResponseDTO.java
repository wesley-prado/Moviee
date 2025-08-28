package com.codemages.Moviee.movie.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "genre", collectionRelation = "genres")
public record GenreResponseDTO(
  @NotBlank(message = "O nome do gênero é obrigatório.") String name
) {
}
