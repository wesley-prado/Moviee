package com.codemages.Moviee.cinema.room.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record RoomCreationDTO(
  @NotBlank(message = "O nome da sala é obrigatório.")
  String name,
  @Valid
  LayoutDTO layout
) {
}
