package com.codemages.Moviee.cinema.room;

// ... imports

import com.codemages.Moviee.cinema.room.constant.RoomStatus;
import com.codemages.Moviee.cinema.room.dto.request.RoomCreationDTO;
import com.codemages.Moviee.cinema.room.dto.request.RowCreationDTO;
import com.codemages.Moviee.cinema.room.dto.response.RoomResponseDTO;
import com.codemages.Moviee.cinema.room.dto.response.RowResponseDTO;
import com.codemages.Moviee.cinema.room.dto.response.SeatResponseDTO;
import com.codemages.Moviee.cinema.room.exception.DuplicateRoomException;
import com.codemages.Moviee.cinema.room.exception.RoomNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {
  private final RoomRepository roomRepository;
  private final ObjectMapper objectMapper;

  public RoomResponseDTO createRoom(RoomCreationDTO roomCreationDTO) {
    if ( roomRepository.existsByName( roomCreationDTO.name() ) ) {
      throw new DuplicateRoomException(
        "Room with name " + roomCreationDTO.name() + " already exists." );
    }

    var room = toEntity( roomCreationDTO );
    var savedRoom = roomRepository.save( room );
    return toResponseDTO( savedRoom );
  }

  public RoomResponseDTO findRoomById(Long id) {
    var room = roomRepository.findById( id )
      .orElseThrow( () -> new RoomNotFoundException( "Room with id " + id + " not found." ) );
    return toResponseDTO( room );
  }

  public List<RoomResponseDTO> findAll() {
    return roomRepository.findAll().stream()
      .map( this::toResponseDTO )
      .toList();
  }

  /**
   * Converte o DTO de criação para a Entidade.
   */
  private Room toEntity(RoomCreationDTO roomCreationDTO) {
    try {
      String rowsAsJson = objectMapper.writeValueAsString( roomCreationDTO.rows() );

      return Room.builder()
        .name( roomCreationDTO.name() )
        .status( RoomStatus.ACTIVE )
        .rows( rowsAsJson )
        .build();
    } catch (JsonProcessingException e) {
      log.error( "Falha ao serializar o layout da sala para JSON", e );
      throw new IllegalArgumentException( "Formato de layout inválido.", e );
    }
  }

  /**
   * Converte a Entidade para o DTO de Resposta.
   */
  private RoomResponseDTO toResponseDTO(Room room) {
    try {
      List<RowCreationDTO> rawRows = objectMapper.readValue(
        room.getRows(),
        objectMapper.getTypeFactory().constructCollectionType( List.class, RowCreationDTO.class )
      );

      List<RowResponseDTO> processedRows = rawRows.stream()
        .map( row -> {
          List<SeatResponseDTO> seatsWithId = IntStream.range( 0, row.seats().size() )
            .mapToObj( i -> {
              var seat = row.seats().get( i );
              return SeatResponseDTO.builder()
                .id( row.name() + (i + 1) ) // Gera o ID do assento (A1, A2, B1, etc.)
                .type( seat.type() )
                .status( seat.status() )
                .build();
            } )
            .toList();
          return RowResponseDTO.builder()
            .name( row.name() )
            .seats( seatsWithId )
            .build();
        } )
        .toList();

      return RoomResponseDTO.builder()
        .id( room.getId() )
        .name( room.getName() )
        .rows( processedRows )
        .build();

    } catch (JsonProcessingException e) {
      log.error(
        "Falha ao deserializar o layout da sala com id {}. JSON: {}",
        room.getId(),
        room.getRows(),
        e
      );
      throw new IllegalStateException( "Não foi possível ler o layout da sala.", e );
    }
  }
}