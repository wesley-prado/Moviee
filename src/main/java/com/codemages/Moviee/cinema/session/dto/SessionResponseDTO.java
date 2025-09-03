package com.codemages.Moviee.cinema.session.dto;

import lombok.Builder;

@Builder
public record SessionResponseDTO(
  Long id,
  String movieTitle,
  String roomName,
  String startTime,
  String endTime
) {
}
