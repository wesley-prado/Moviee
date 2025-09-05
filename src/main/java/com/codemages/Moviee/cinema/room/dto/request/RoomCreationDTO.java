package com.codemages.Moviee.cinema.room.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record RoomCreationDTO(
  @NotBlank(message = "O nome da sala é obrigatório e deve ser único.")
  String name,
  @Valid
  @NotEmpty
  List<RowCreationDTO> rows
) {
}