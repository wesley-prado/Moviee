package com.codemages.Moviee.user.controllers.v1;

import com.codemages.Moviee.exceptions.ForbiddenOperationException;
import com.codemages.Moviee.security.config.AuthContextHelper;
import com.codemages.Moviee.user.UserService;
import com.codemages.Moviee.user.assemblers.UserModelAssembler;
import com.codemages.Moviee.user.dto.UserCreateDTO;
import com.codemages.Moviee.user.dto.UserResponseDTO;
import com.codemages.Moviee.user.enums.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserModelAssembler userModelAssembler;
  private final UserService userService;
  private final AuthContextHelper authContextHelper;

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<CollectionModel<EntityModel<UserResponseDTO>>> getUsers() {

    List<UserResponseDTO> users = userService.findAll();

    return ResponseEntity.ok().contentType( MediaTypes.HAL_JSON )
      .body( userModelAssembler.toCollectionModel( users ) );
  }

  @PreAuthorize("hasAuthority('MODERATOR')")
  @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<EntityModel<UserResponseDTO>> getUser(
    @PathVariable UUID id
  ) {
    UserResponseDTO dto = userService.findById( id );

    return ResponseEntity.ok().contentType( MediaTypes.HAL_JSON )
      .body( userModelAssembler.toModel( dto ) );

  }

  //TODO: Corrigir permissão para que um usuário anônimo possa criar uma conta
  @PostMapping(consumes = "application/json", produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<EntityModel<UserResponseDTO>> createUser(
    @RequestBody @Valid UserCreateDTO dto
  ) {
    List<String> specialRoles = List.of(
      Role.ADMIN.name(),
      Role.MODERATOR.name()
    );

    if ( specialRoles.contains( dto.role() )
      && !authContextHelper.isUserAdmin() ) {
      throw new ForbiddenOperationException(
        "Only admins can create other admins or moderators." );
    }

    UserResponseDTO result = userService.createUser( dto );

    return ResponseEntity.status( HttpStatus.CREATED )
      .contentType( MediaTypes.HAL_JSON )
      .body( userModelAssembler.toModel( result ) );
  }
}
