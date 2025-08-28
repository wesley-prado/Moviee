package com.codemages.Moviee.movie;

import com.codemages.Moviee.movie.enums.Genre;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  @Range(min = 1878)
  private Integer year;

  @ElementCollection(targetClass = Genre.class)
  @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private List<Genre> genres = new ArrayList<>();

  @Column(columnDefinition = "TEXT")
  private String description;

  @Min(0)
  @Max(10)
  private int rating;

  @Min(50)
  @Column(nullable = false)
  private int durationInMinutes;

  @Column(nullable = false)
  private String director;

  @ElementCollection
  @CollectionTable(name = "movie_cast", joinColumns = @JoinColumn(name = "movie_id"))
  @Column(name = "cast_member")
  @Builder.Default
  private List<String> castMembers = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "movie_writers", joinColumns = @JoinColumn(name = "movie_id"))
  @Builder.Default
  private List<String> writers = new ArrayList<>();
}
