package com.codemages.Moviee.controllers.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemages.Moviee.assemblers.UserModelAssembler;
import com.codemages.Moviee.dtos.UserCreateDTO;
import com.codemages.Moviee.dtos.UserResponseDTO;
import com.codemages.Moviee.entities.Role;
import com.codemages.Moviee.exceptions.global.ForbiddenOperationException;
import com.codemages.Moviee.exceptions.user.UserNotCreatedException;
import com.codemages.Moviee.exceptions.user.UserNotFoundException;
import com.codemages.Moviee.security.AuthContextHelper;
import com.codemages.Moviee.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  @Autowired
  private UserModelAssembler userModelAssembler;
  @Autowired
  private UserService userService;
  @Autowired
  private AuthContextHelper authContextHelper;

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

    if ( dto == null ) {
      throw new UserNotFoundException( "User with ID " + id + " not found." );
    }

    return ResponseEntity.ok().contentType( MediaTypes.HAL_JSON )
      .body( userModelAssembler.toModel( dto ) );

  }

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

    if ( result == null ) {
      throw new UserNotCreatedException(
        "User could not be created. Please check the input data." );
    }

    return ResponseEntity.status( HttpStatus.CREATED )
      .contentType( MediaTypes.HAL_JSON )
      .body( userModelAssembler.toModel( result ) );
  }
}
