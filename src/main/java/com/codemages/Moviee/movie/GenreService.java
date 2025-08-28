package com.codemages.Moviee.movie;

import com.codemages.Moviee.movie.dto.GenreResponseDTO;
import com.codemages.Moviee.movie.enums.Genre;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

  public List<GenreResponseDTO> convertToGenreResponseDTO(Genre[] genres) {
    return convertToGenreResponseDTO( Arrays.asList( genres ) );
  }

  public List<GenreResponseDTO> convertToGenreResponseDTO(List<Genre> genres) {
    return genres.stream()
      .map( genre -> new GenreResponseDTO( genre.label ) )
      .collect( Collectors.toList() );
  }
}