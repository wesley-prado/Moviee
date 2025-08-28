package com.codemages.Moviee.movie.enums;

import org.springframework.hateoas.server.core.Relation;

@Relation(value = "genre", collectionRelation = "genres")
public enum Genre {
  ACTION( "Action" ),
  ADVENTURE( "Adventure" ),
  ANIMATION( "Animation" ),
  COMEDY( "Comedy" ),
  DRAMA( "Drama" ),
  HISTORICAL( "Historical" ),
  HORROR( "Horror" ),
  TERROR( "Terror" ),
  SCIENCE_FICTION( "Science Fiction" ),
  WESTERN( "Western" ),
  ROMANCE( "Romance" );

  public final String label;

  Genre(String label) {
    this.label = label;
  }
}
