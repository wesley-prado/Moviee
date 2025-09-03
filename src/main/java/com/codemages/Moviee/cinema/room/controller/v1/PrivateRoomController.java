package com.codemages.Moviee.cinema.room.controller.v1;

import com.codemages.Moviee.cinema.room.RoomService;
import com.codemages.Moviee.cinema.room.assembler.RoomModelAssembler;
import com.codemages.Moviee.cinema.room.dto.RoomCreationDTO;
import com.codemages.Moviee.cinema.room.dto.RoomResponseDTO;
import com.codemages.Moviee.constant.ControllerConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerConstant.API_BASE + "/v1/rooms")
@RequiredArgsConstructor
public class PrivateRoomController {
  private final RoomService roomService;
  private final RoomModelAssembler roomModelAssembler =
    new RoomModelAssembler();

  @PostMapping
  @PreAuthorize("hasAuthority('MODERATOR')")
  public ResponseEntity<EntityModel<RoomResponseDTO>> createRoom(
    @RequestBody
    RoomCreationDTO createRequest
  ) {
    RoomResponseDTO createdRoom = roomService.createRoom( createRequest );
    EntityModel<RoomResponseDTO> roomModel =
      roomModelAssembler.toModel( createdRoom );

    return ResponseEntity.created( roomModel.getRequiredLink( IanaLinkRelations.SELF ).toUri() )
      .body( roomModel );
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('MODERATOR')")
  public ResponseEntity<EntityModel<RoomResponseDTO>> findRoomById(
    @PathVariable Long id
  ) {
    RoomResponseDTO room = roomService.findRoomById( id );
    return ResponseEntity.ok( roomModelAssembler.toModel( room ) );
  }
}
