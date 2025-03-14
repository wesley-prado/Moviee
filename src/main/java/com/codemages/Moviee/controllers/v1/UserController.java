package com.codemages.moviee.controllers.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemages.moviee.assemblers.UserModelAssembler;
import com.codemages.moviee.dtos.UserCreateDTO;
import com.codemages.moviee.dtos.UserResponseDTO;
import com.codemages.moviee.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
        private static final MediaType DEFAULT_MEDIA_TYPE = MediaType
                        .parseMediaType("application/hal+json");

        @Autowired
        private UserModelAssembler userModelAssembler;
        @Autowired
        private UserService userService;

        @GetMapping(produces = "application/hal+json")
        public ResponseEntity<CollectionModel<EntityModel<UserResponseDTO>>> getUsersV1() {

                return ResponseEntity.status(HttpStatus.OK)
                                .contentType(DEFAULT_MEDIA_TYPE)
                                .body(userModelAssembler.toCollectionModel(
                                                userService.findAll()));
        }

        @GetMapping(value = "/{id}", produces = "application/hal+json")
        public ResponseEntity<EntityModel<UserResponseDTO>> getUserV1(
                        @PathVariable UUID id) {
                UserResponseDTO dto = userService.findById(id);

                if (dto == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .build();
                }

                return ResponseEntity.status(HttpStatus.OK)
                                .body(userModelAssembler.toModel(dto));
        }

        @PostMapping(produces = "application/hal+json")
        public ResponseEntity<EntityModel<UserResponseDTO>> createUserV1(
                        @RequestBody @Valid UserCreateDTO dto) {
                UserResponseDTO result = userService.save(dto);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(userModelAssembler.toModel(result));
        }
}
