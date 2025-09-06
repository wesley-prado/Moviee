package com.codemages.Moviee.cinema.room.dto.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record RowResponseDTO(
  String name,
  @Valid
  @NotEmpty
  List<SeatResponseDTO> seats
) {
}
