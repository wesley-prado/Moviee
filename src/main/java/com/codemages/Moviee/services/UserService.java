package com.codemages.moviee.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemages.moviee.dtos.RoleResponseDTO;
import com.codemages.moviee.dtos.UserCreateDTO;
import com.codemages.moviee.dtos.UserResponseDTO;
import com.codemages.moviee.entities.Role;
import com.codemages.moviee.entities.RoleStatus;
import com.codemages.moviee.entities.User;
import com.codemages.moviee.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  @Autowired
  private RoleService roleService;
  @Autowired
  private UserRepository userRepository;

  public UserResponseDTO save(UserCreateDTO userCreateDto) {
    RoleResponseDTO role = roleService.findByName(userCreateDto.role());

    User u = new User(null, userCreateDto.username(), userCreateDto.email(),
        userCreateDto.password(),
        new Role(role.id(), role.name(), RoleStatus.ACTIVE));

    return toUserResponseDTO(userRepository.save(u));
  }

  public List<UserResponseDTO> findAll() {
    return userRepository.findAll().stream().map(this::toUserResponseDTO)
        .toList();
  }

  public UserResponseDTO findById(UUID id) {
    return toUserResponseDTO(userRepository.findById(id).orElse(null));
  }

  private UserResponseDTO toUserResponseDTO(User u) {
    return u == null ? null
        : new UserResponseDTO(u.getId(), u.getUsername(), u.getEmail(),
            new RoleResponseDTO(u.getRole().getId(), u.getRole().getName()),
            u.getStatus().toString());
  }

  public long count() {
    return userRepository.count();
  }
}
