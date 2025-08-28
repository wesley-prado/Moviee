package com.codemages.Moviee.user;

import com.codemages.Moviee.security.password.interfaces.PasswordValidator;
import com.codemages.Moviee.user.dto.PrivateUserCreationDTO;
import com.codemages.Moviee.user.dto.PublicUserCreationDTO;
import com.codemages.Moviee.user.dto.UserResponseDTO;
import com.codemages.Moviee.user.enums.UserStatus;
import com.codemages.Moviee.user.exceptions.DuplicateUserException;
import com.codemages.Moviee.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final PasswordValidator passwordValidator;
  private final UserMapper userMapper = UserMapper.INSTANCE;

  @Transactional
  public UserResponseDTO createPublicUser(PublicUserCreationDTO publicDto) {
    UserCreationRequest request = UserCreationRequest.from( publicDto );

    return createUser( request );
  }

  @Transactional
  public UserResponseDTO createPrivateUser(PrivateUserCreationDTO privateDto) {
    UserCreationRequest request = UserCreationRequest.from( privateDto );

    return createUser( request );
  }

  private UserResponseDTO createUser(UserCreationRequest request) {
    validateUser( request );

    var user = userMapper.toEntity( request );
    user.setStatus( UserStatus.ACTIVE );
    user.setPassword( passwordEncoder.encode( user.getPassword() ) );

    return userMapper.toResponseDTO( userRepository.save( user ) );
  }

  private void validateUser(UserCreationRequest request) {
    if ( !passwordValidator.isValid( request.password() ) ) {
      throw new IllegalArgumentException( "Password is not valid" );
    }

    Optional<User> u = userRepository.findOptionalByEmailOrUsername(
      request.email(),
      request.username()
    );

    if ( u.isEmpty() ) {
      return;
    }

    if ( u.get().getEmail().equals( request.email() ) ) {
      throw new DuplicateUserException( "Email already registered" );
    }

    if ( u.get().getUsername().equals( request.username() ) ) {
      throw new DuplicateUserException( "Username already taken" );
    }
  }

  @Transactional(readOnly = true)
  public List<UserResponseDTO> findAll() {
    return userRepository.findAll().stream().map( userMapper::toResponseDTO ).toList();
  }

  @Transactional(readOnly = true)
  public UserResponseDTO findById(UUID id) {
    return userMapper.toResponseDTO( userRepository.findById( id )
      .orElseThrow( () -> new UserNotFoundException( "User not found with id: " + id ) ) );
  }

  @Transactional(readOnly = true)
  public boolean isUsernameTaken(String username) {
    Optional<User> user = userRepository.findByUsername( username );

    return user.isPresent();
  }
}
