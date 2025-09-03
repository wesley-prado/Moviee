package com.codemages.Moviee.cinema.session.controller.v1;

import com.codemages.Moviee.cinema.session.SessionService;
import com.codemages.Moviee.cinema.session.assembler.SessionAssembler;
import com.codemages.Moviee.cinema.session.dto.SessionCreationDTO;
import com.codemages.Moviee.cinema.session.dto.SessionResponseDTO;
import com.codemages.Moviee.constant.ControllerConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerConstant.API_BASE + "/v1/sessions")
@RequiredArgsConstructor
public class PrivateSessionController {
  private final SessionService sessionService;
  private final SessionAssembler sessionModelAssembler = new SessionAssembler();

  @PreAuthorize("hasAuthority('MODERATOR')")
  @PostMapping
  public ResponseEntity<EntityModel<SessionResponseDTO>> createSession(
    @Validated @RequestBody SessionCreationDTO createRequest
  ) {
    var response = sessionService.save( createRequest );
    var model = sessionModelAssembler.toModel( response );

    return ResponseEntity.created( model.getRequiredLink( IanaLinkRelations.SELF ).toUri() )
      .body( sessionModelAssembler.toModel( response ) );
  }

  @PreAuthorize("hasAuthority('MODERATOR')")
  @GetMapping("/{id}")
  public ResponseEntity<EntityModel<SessionResponseDTO>> findById(@PathVariable Long id) {
    var response = sessionService.findById( id );
    return ResponseEntity.ok( sessionModelAssembler.toModel( response ) );
  }
}
