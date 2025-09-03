package com.codemages.Moviee.cinema.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TicketCreationDTO(
  @NotNull(message = "O ID da sessão é obrigatório.")
  Long sessionId,
  @NotBlank(message = "O número do assento é obrigatório.")
  @Pattern(regexp = "^[A-Z][0-9]{1,2}$",
    message = "O número do assento deve seguir o padrão (ex: F12).")
  String seatNumber
) {
}
