package com.codemages.Moviee.security.config;

import com.codemages.Moviee.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    var optionalUser = userRepository.findByUsername( username );

    if ( optionalUser.isEmpty() ) {
      throw new UsernameNotFoundException( "Username not found!" );
    }

    var user = optionalUser.get();

    return org.springframework.security.core.userdetails.User.builder()
      .username( user.getUsername() ).password( user.getPassword() )
      .authorities( new SimpleGrantedAuthority( user.getRole().name() ) )
      .build();
  }
}
