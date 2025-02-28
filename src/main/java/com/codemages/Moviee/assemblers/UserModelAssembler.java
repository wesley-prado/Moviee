package com.codemages.moviee.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.codemages.moviee.controllers.v1.UserController;
import com.codemages.moviee.dtos.UserResponseDTO;

import lombok.NonNull;

@Component
public class UserModelAssembler implements
                RepresentationModelAssembler<UserResponseDTO, EntityModel<UserResponseDTO>> {

        @NonNull
        @Override
        public EntityModel<UserResponseDTO> toModel(UserResponseDTO dto) {
                Link selfLink = WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder
                                                .methodOn(UserController.class)
                                                .getUserV1(dto.id()))
                                .withSelfRel();
                Link usersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                                .methodOn(UserController.class).getUsersV1())
                                .withRel("users");

                return EntityModel.of(dto, selfLink, usersLink);
        }
}
