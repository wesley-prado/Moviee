package com.codemages.Moviee.cinema.room.dto;

import lombok.Builder;

@Builder
public record RoomResponseDTO(
  Long id,
  String name,
  LayoutDTO roomLayout
) {
}
