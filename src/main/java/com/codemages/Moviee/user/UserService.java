package com.codemages.Moviee.user;

import com.codemages.Moviee.security.password.interfaces.PasswordValidator;
import com.codemages.Moviee.user.dto.UserCreateDTO;
import com.codemages.Moviee.user.dto.UserResponseDTO;
import com.codemages.Moviee.user.enums.DocumentType;
import com.codemages.Moviee.user.enums.Role;
import com.codemages.Moviee.user.exceptions.DuplicateUserException;
import com.codemages.Moviee.user.exceptions.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
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

  @Transactional(readOnly = true)
  public List<UserResponseDTO> findAll() {
    return userRepository.findAll().stream().map( this::toUserResponseDTO ).toList();
  }

  @Transactional(readOnly = true)
  public UserResponseDTO findById(UUID id) {
    return toUserResponseDTO( userRepository.findById( id )
      .orElseThrow( () -> new UserNotFoundException( "User not found with id: " + id ) ) );
  }

  @Transactional(readOnly = true)
  public boolean isUsernameTaken(String username) {
    Optional<User> user = userRepository.findByUsername( username );

    return user.isPresent();
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
}
