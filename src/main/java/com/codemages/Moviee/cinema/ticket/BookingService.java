package com.codemages.Moviee.cinema.ticket;

import com.codemages.Moviee.cinema.ticket.dto.TicketCreationDTO;
import com.codemages.Moviee.cinema.ticket.dto.TicketResponseDTO;
import com.codemages.Moviee.cinema.ticket.exception.SeatAlreadyBookedException;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
  public TicketResponseDTO bookTicket(TicketCreationDTO dto) {
    throw new SeatAlreadyBookedException(
      "Oops! Alguém foi mais rápido. O assento " + dto.seatNumber() + " já foi reservado." );
  }
}
