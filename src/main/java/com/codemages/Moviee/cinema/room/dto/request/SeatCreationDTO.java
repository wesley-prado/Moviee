package com.codemages.Moviee.cinema.room.dto.request;

import com.codemages.Moviee.cinema.room.constant.SeatStatus;
import com.codemages.Moviee.cinema.room.constant.SeatType;
import jakarta.validation.constraints.NotNull;

public record SeatCreationDTO(
  @NotNull(message = "O tipo do assento é obrigatório.")
  SeatType type,
  @NotNull(message = "O status do assento é obrigatório.")
  SeatStatus status
) {
}
