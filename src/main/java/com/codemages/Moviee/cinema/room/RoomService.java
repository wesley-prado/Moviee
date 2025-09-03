package com.codemages.Moviee.cinema.room;

import com.codemages.Moviee.cinema.room.constant.RoomStatus;
import com.codemages.Moviee.cinema.room.dto.LayoutDTO;
import com.codemages.Moviee.cinema.room.dto.RoomCreationDTO;
import com.codemages.Moviee.cinema.room.dto.RoomResponseDTO;
import com.codemages.Moviee.cinema.room.exception.DuplicateRoomException;
import com.codemages.Moviee.cinema.room.exception.RoomNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {
  private final RoomRepository roomRepository;
  private final ObjectMapper objectMapper;

  public RoomResponseDTO createRoom(RoomCreationDTO roomCreationDTO) {
    var room = toEntity( roomCreationDTO );

    try {
      roomRepository.save( room );
      return toResponseDTO( room );
    } catch (DataIntegrityViolationException e) {
      throw new DuplicateRoomException( "Room with name " + room.getName() + " already exists." );
    }
  }

  public RoomResponseDTO findRoomById(Long id) {
    var room = roomRepository.findById( id )
      .orElseThrow( () -> new RoomNotFoundException( "Room with id " + id + " not found." ) );
    return toResponseDTO( room );
  }

  public List<RoomResponseDTO> findAll() {
    var rooms = roomRepository.findAll();
    return rooms.stream()
      .map( this::toResponseDTO )
      .toList();
  }

  private RoomResponseDTO toResponseDTO(Room room) {
    try {
      log.info( "Room layout JSON: {}", room.getLayout() );
      LayoutDTO layoutDTO = objectMapper.readValue( room.getLayout(), LayoutDTO.class );

      return RoomResponseDTO.builder()
        .id( room.getId() )
        .name( room.getName() )
        .roomLayout( layoutDTO )
        .build();
    } catch (JsonProcessingException e) {
      log.error( "Falha ao deserializar o layout da sala com id {}", room.getId(), e );
      throw new IllegalStateException( "Não foi possível ler o layout da sala.", e );
    }
  }

  private Room toEntity(RoomCreationDTO dto) {
    var layout = dto.layout();
    try {
      String layoutAsJson = objectMapper.writeValueAsString( layout );

      return Room.builder().name( dto.name() )
        .status( RoomStatus.ACTIVE )
        .layout( layoutAsJson )
        .build();
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException( "Falha ao serializar o layout da sala para JSON", e );
    }
  }
}
