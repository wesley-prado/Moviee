package com.codemages.Moviee.cinema.room.dto.response;

import com.codemages.Moviee.cinema.room.constant.SeatStatus;
import com.codemages.Moviee.cinema.room.constant.SeatType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record SeatResponseDTO(
  @Pattern(regexp = "^[A-Z][0-9]{1,2}$]",
    message = "O ID do assento deve começar com uma letra maiúscula seguida de um ou dois dígitos" +
      " (ex: A1, B12).")
  String id,
  @NotNull(message = "O tipo do assento é obrigatório.")
  SeatType type,
  @NotNull(message = "O status do assento é obrigatório.")
  SeatStatus status
) {
}
