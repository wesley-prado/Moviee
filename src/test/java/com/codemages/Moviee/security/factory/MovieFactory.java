package com.codemages.Moviee.security.factory;

import com.codemages.Moviee.cinema.movie.Movie;
import com.codemages.Moviee.cinema.movie.constant.Genre;
import com.codemages.Moviee.cinema.movie.dto.GenreResponseDTO;
import com.codemages.Moviee.cinema.movie.dto.MovieResponseDTO;
import com.codemages.Moviee.cinema.movie.dto.PrivateMovieCreationDTO;
import com.codemages.Moviee.movie.test.util.IdGenerator;

import java.util.List;

public class MovieFactory {
  private static final String entityName = Movie.class.getTypeName();

  public static PrivateMovieCreationDTO createMovieRequest() {
    return new MovieFactory.Builder().build();
  }

  public static MovieResponseDTO createMovieResponseDTO() {
    return MovieResponseDTO.builder()
      .id( IdGenerator.nextId( entityName ) )
      .title( "Inception" )
      .year( 2010 )
      .genres( List.of(
        new GenreResponseDTO( Genre.SCI_FI ),
        new GenreResponseDTO( Genre.ACTION )
      ) )
      .description( "A mind-bending thriller about dream invasion." )
      .build();
  }

  private static class Id {
    private static long value;

    private static synchronized long getValue() {
      return value++;
    }
  }

  public static class Builder {
    private String title = "Inception";
    private Integer year = 2010;
    private List<Genre> genres = List.of( Genre.SCI_FI, Genre.ACTION );
    private String description = "A mind-bending thriller about dream invasion.";
    private int rating = 8;
    private int durationInMinutes = 148;
    private String director = "Christopher Nolan";
    private String[] cast = new String[]{
      "Leonardo DiCaprio",
      "Joseph Gordon-Levitt",
      "Ellen Page"
    };
    private String[] writers = new String[]{ "Christopher Nolan" };

    public Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder withYear(Integer year) {
      this.year = year;
      return this;
    }

    public Builder withGenres(List<Genre> genres) {
      this.genres = genres;
      return this;
    }

    public Builder withDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder withRating(int rating) {
      this.rating = rating;
      return this;
    }

    public Builder withDurationInMinutes(int durationInMinutes) {
      this.durationInMinutes = durationInMinutes;
      return this;
    }

    public Builder withDirector(String director) {
      this.director = director;
      return this;
    }

    public Builder withCast(String[] cast) {
      this.cast = cast;
      return this;
    }

    public Builder withWriters(String[] writers) {
      this.writers = writers;
      return this;
    }

    public PrivateMovieCreationDTO build() {
      return new PrivateMovieCreationDTO(
        title,
        year,
        genres,
        description,
        rating,
        durationInMinutes,
        director,
        cast,
        writers
      );
    }
  }
}
