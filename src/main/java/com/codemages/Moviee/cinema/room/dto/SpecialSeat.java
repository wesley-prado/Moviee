package com.codemages.Moviee.cinema.room.dto;

import com.codemages.Moviee.cinema.room.constant.SeatType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SpecialSeat(
  @NotBlank(message = "O preenchimento do id do assento é obrigatório")
  String id,
  @NotNull(message = "O preenchimento do tipo do assento é obrigatório")
  @Pattern(regexp = "^[A-Z][0-9]{1,2}$",
    message = "O ID do assento deve seguir o padrão (ex: F12).")
  SeatType type
) {
}
