package com.codemages.Moviee.controllers.v1;

import com.codemages.Moviee.assemblers.RoleModelAssembler;
import com.codemages.Moviee.dtos.RoleResponseDTO;
import com.codemages.Moviee.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
  @Autowired
  private RoleModelAssembler roleModelAssembler;

  @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<CollectionModel<EntityModel<RoleResponseDTO>>> getRoles() {
    List<RoleResponseDTO> roles = Arrays.stream( Role.values() )
      .map( Role::name )
      .map( RoleResponseDTO::new )
      .toList();

    return ResponseEntity.ok().contentType( MediaTypes.HAL_JSON )
      .body( roleModelAssembler
        .toCollectionModel( roles ) );
  }
}
