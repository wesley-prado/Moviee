package com.codemages.Moviee.cinema.session.dto;

import lombok.Builder;
import org.springframework.hateoas.server.core.Relation;

import java.time.ZonedDateTime;

@Builder
@Relation(itemRelation = "session", collectionRelation = "sessions")
public record SessionResponseDTO(
  Long id,
  String movieTitle,
  String roomName,
  ZonedDateTime startTime,
  ZonedDateTime endTime
) {
}
