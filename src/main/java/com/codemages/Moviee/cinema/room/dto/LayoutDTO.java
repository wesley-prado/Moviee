package com.codemages.Moviee.cinema.room.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record LayoutDTO(
  @NotEmpty(message = "A lista de nomes de fileiras não pode ser vazia.")
  List<String> rowNames,

  @NotNull(message = "A quantidade de assentos por fileira é obrigatória.")
  @Positive(message = "A quantidade de assentos deve ser um número positivo.")
  Integer seatsPerRow,

  @Valid
  List<SpecialSeat> specialSeats
) {
}
