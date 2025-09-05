package com.codemages.Moviee.cinema.room.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RowCreationDTO(
  String name,
  @Valid
  @NotEmpty
  List<SeatCreationDTO> seats
) {
}
