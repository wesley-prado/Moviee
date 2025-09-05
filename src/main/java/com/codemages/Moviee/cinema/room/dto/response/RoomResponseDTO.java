package com.codemages.Moviee.cinema.room.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RoomResponseDTO(
  Long id,
  String name,
  List<RowResponseDTO> rows
) {
}
