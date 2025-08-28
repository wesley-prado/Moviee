package com.codemages.Moviee.cinema.movie;

import com.codemages.Moviee.cinema.movie.constant.Genre;
import com.codemages.Moviee.cinema.movie.dto.GenreResponseDTO;
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
      .map( GenreResponseDTO::new )
      .collect( Collectors.toList() );
  }
}