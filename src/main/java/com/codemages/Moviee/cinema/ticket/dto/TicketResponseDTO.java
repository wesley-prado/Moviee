package com.codemages.Moviee.cinema.ticket.dto;

public record TicketResponseDTO(
  Long id,
  Long sessionId,
  String seatNumber,
  String customerName
) {
}
