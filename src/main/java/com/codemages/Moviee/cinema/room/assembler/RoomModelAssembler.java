package com.codemages.Moviee.cinema.room.assembler;

import com.codemages.Moviee.cinema.room.controller.v1.PrivateRoomController;
import com.codemages.Moviee.cinema.room.controller.v1.PublicRoomController;
import com.codemages.Moviee.cinema.room.dto.RoomResponseDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class RoomModelAssembler
  implements RepresentationModelAssembler<RoomResponseDTO, EntityModel<RoomResponseDTO>> {
  @Override
  public EntityModel<RoomResponseDTO> toModel(@NotNull RoomResponseDTO dto) {
    var selfLink =
      linkTo( methodOn( PrivateRoomController.class ).findRoomById( dto.id() ) ).withSelfRel();

    return EntityModel.of( dto, selfLink );
  }

  @Override
  public CollectionModel<EntityModel<RoomResponseDTO>> toCollectionModel(
    @NotNull Iterable<?
      extends RoomResponseDTO> dtos
  ) {
    var models = ((List<RoomResponseDTO>) dtos).stream()
      .map( room -> toModel( room ) )
      .toList();

    var collectionLink =
      linkTo( methodOn( PublicRoomController.class ).findAll() ).withRel( "rooms" );

    return CollectionModel.of( models, collectionLink );
  }
}
