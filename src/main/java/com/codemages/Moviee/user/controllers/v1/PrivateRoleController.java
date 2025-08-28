package com.codemages.Moviee.user.controllers.v1;

import com.codemages.Moviee.constants.ControllerConstants;
import com.codemages.Moviee.user.assemblers.RoleModelAssembler;
import com.codemages.Moviee.user.dto.RoleResponseDTO;
import com.codemages.Moviee.user.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(ControllerConstants.API_BASE + "/v1/roles")
@RequiredArgsConstructor
public class PrivateRoleController {
  private final RoleModelAssembler roleModelAssembler;

  @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
  @PreAuthorize("hasAuthority('MODERATOR')")
  public ResponseEntity<CollectionModel<EntityModel<RoleResponseDTO>>> getRoles() {
    List<RoleResponseDTO> roles = Arrays.stream( Role.values() )
      .map( role -> new RoleResponseDTO( role.getDisplayName(), role.getDescription() ) )
      .toList();

    return ResponseEntity.ok()
      .contentType( MediaTypes.HAL_JSON )
      .body( roleModelAssembler.toCollectionModel( roles ) );
  }
}
