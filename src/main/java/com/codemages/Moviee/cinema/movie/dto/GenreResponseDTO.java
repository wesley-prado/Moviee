package com.codemages.Moviee.cinema.movie.dto;

import com.codemages.Moviee.cinema.movie.constant.Genre;
import jakarta.validation.constraints.NotBlank;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "genre", collectionRelation = "genres")
public record GenreResponseDTO(
  @NotBlank(message = "O nome do gênero é obrigatório.") Genre genre
) {
}
