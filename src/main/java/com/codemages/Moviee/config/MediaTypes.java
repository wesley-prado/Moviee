package com.codemages.Moviee.config;

import org.springframework.http.MediaType;

public class MediaTypes {
  private MediaTypes() {}

  public static final String RESPONSE_TYPE = "application/hal+json";
  public static final MediaType DEFAULT_MEDIA_TYPE = MediaType
    .parseMediaType( RESPONSE_TYPE );
}
