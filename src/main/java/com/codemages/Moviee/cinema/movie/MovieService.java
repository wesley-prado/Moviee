package com.codemages.Moviee.cinema.movie;

import com.codemages.Moviee.cinema.movie.dto.MovieResponseDTO;
import com.codemages.Moviee.cinema.movie.dto.PrivateMovieCreationDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
  private final GenreService genreService;
  private final MovieRepository movieRepository;

  public List<MovieResponseDTO> findAll() {
    List<Movie> movies = movieRepository.findAll();

    return toResponseDTOCollection( movies );
  }

  private List<MovieResponseDTO> toResponseDTOCollection(List<Movie> movies) {
    return movies.stream().map( this::toResponseDTO ).toList();
  }

  private MovieResponseDTO toResponseDTO(Movie movie) {
    return MovieResponseDTO.builder()
      .id( movie.getId() )
      .title( movie.getTitle() )
      .year( movie.getYear() )
      .genres( genreService.convertToGenreResponseDTO( movie.getGenres() ) )
      .description( movie.getDescription() )
      .build();
  }

  private Movie toModel(@Valid PrivateMovieCreationDTO dto) {
    return Movie.builder()
      .title( dto.title() )
      .year( dto.year() )
      .genres( dto.genres() )
      .description( dto.description() )
      .rating( dto.rating() )
      .durationInMinutes( dto.durationInMinutes() )
      .director( dto.director() )
      .castMembers( List.of( dto.cast() ) )
      .writers( List.of( dto.writers() ) )
      .build();
  }

  public MovieResponseDTO save(PrivateMovieCreationDTO dto) {
    Movie movie = toModel( dto );

    movie = movieRepository.save( movie );

    return toResponseDTO( movie );
  }
}
