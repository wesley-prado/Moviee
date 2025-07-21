package com.codemages.Moviee.services;

import com.codemages.Moviee.dtos.UserCreateDTO;
import com.codemages.Moviee.dtos.UserResponseDTO;
import com.codemages.Moviee.entities.DocumentType;
import com.codemages.Moviee.entities.Role;
import com.codemages.Moviee.entities.User;
import com.codemages.Moviee.exceptions.user.DuplicateUserException;
import com.codemages.Moviee.exceptions.user.UserNotFoundException;
import com.codemages.Moviee.repositories.UserRepository;
import com.codemages.Moviee.utils.password.interfaces.PasswordValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final PasswordValidator passwordValidator;

  public UserService(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder,
    PasswordValidator passwordValidator
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.passwordValidator = passwordValidator;
  }

  public UserResponseDTO createUser(UserCreateDTO userCreateDto) {
    validateUser( userCreateDto );

    return toUserResponseDTO( userRepository.save( createUserEntity( userCreateDto ) ) );
  }

  private void validateUser(UserCreateDTO userCreateDto) {
    if ( !passwordValidator.isValid( userCreateDto.password() ) ) {
      throw new IllegalArgumentException( "Password is not valid" );
    }

    Optional<User> u = userRepository.findOptionalByEmailOrUsername(
      userCreateDto.email(),
      userCreateDto.username()
    );

    if ( u.isEmpty() ) {
      return;
    }

    if ( u.get().getEmail().equals( userCreateDto.email() ) ) {
      throw new DuplicateUserException( "Email already registered" );
    }

    if ( u.get().getUsername().equals( userCreateDto.username() ) ) {
      throw new DuplicateUserException( "Username already registered" );
    }
  }

  private User createUserEntity(UserCreateDTO userCreateDto) {
    return new User(
      null,
      userCreateDto.username(),
      userCreateDto.email(),
      passwordEncoder.encode( userCreateDto.password() ),
      Role.valueOf( userCreateDto.role() ),
      userCreateDto.document(),
      DocumentType.fromString( userCreateDto.documentType() )
    );
  }

  public List<UserResponseDTO> findAll() {
    return userRepository.findAll().stream().map( this::toUserResponseDTO ).toList();
  }

  public UserResponseDTO findById(UUID id) {
    return toUserResponseDTO( userRepository.findById( id )
      .orElseThrow( () -> new UserNotFoundException( "User not found with id: " + id ) ) );
  }

  private UserResponseDTO toUserResponseDTO(User u) {
    return new UserResponseDTO(
      u.getId(),
      u.getUsername(),
      u.getEmail(),
      u.getRole().name(),
      u.getStatus().toString()
    );
  }

  public long count() {
    return userRepository.count();
  }
}
