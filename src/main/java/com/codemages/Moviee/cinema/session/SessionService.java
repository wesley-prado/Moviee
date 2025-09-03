package com.codemages.Moviee.cinema.session;

import com.codemages.Moviee.cinema.movie.MovieRepository;
import com.codemages.Moviee.cinema.movie.exception.MovieNotFoundException;
import com.codemages.Moviee.cinema.room.RoomRepository;
import com.codemages.Moviee.cinema.room.exception.RoomNotFoundException;
import com.codemages.Moviee.cinema.session.dto.SessionCreationDTO;
import com.codemages.Moviee.cinema.session.dto.SessionResponseDTO;
import com.codemages.Moviee.cinema.session.exception.SessionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SessionService {
  private final SessionRepository sessionRepository;
  private final MovieRepository movieRepository;
  private final RoomRepository roomRepository;

  @Transactional(readOnly = true)
  public SessionResponseDTO findById(Long id) {
    return sessionRepository.findById( id )
      .map( this::toSessionResponseDTO )
      .orElseThrow( () -> new SessionNotFoundException(
        "Sessão com o Id " + id + " não encontrada" ) );
  }

  @Transactional
  public SessionResponseDTO save(SessionCreationDTO dto) {
    var movie = movieRepository.findById( dto.movieId() )
      .orElseThrow( () -> new MovieNotFoundException(
        "Filme com o Id " + dto.movieId() + " não encontrado" ) );
    var room = roomRepository.findById( dto.roomId() )
      .orElseThrow( () -> new RoomNotFoundException(
        "Sala com o Id " + dto.roomId() + " não encontrada" ) );

    LocalDateTime endTime = dto.startTime().plusMinutes( movie.getDurationInMinutes() );

    var session = Session.builder()
      .movie( movie ).room( room ).startTime( dto.startTime() ).endTime( endTime ).build();

    var savedEntity = sessionRepository.save( session );
    return toSessionResponseDTO( savedEntity );
  }

  private SessionResponseDTO toSessionResponseDTO(Session session) {
    return SessionResponseDTO.builder()
      .id( session.getId() )
      .movieTitle( session.getMovie().getTitle() )
      .roomName( session.getRoom().getName() )
      .startTime( session.getStartTime().toString() )
      .endTime( session.getEndTime().toString() )
      .build();
  }
}
