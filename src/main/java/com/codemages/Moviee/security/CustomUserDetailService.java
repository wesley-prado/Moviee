package com.codemages.moviee.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codemages.moviee.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		var optionalUser = userRepository.findByUsername(username);

		if (!optionalUser.isPresent()) {
			throw new UsernameNotFoundException("Username not found!");
		}

		var user = optionalUser.get();

		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getUsername()).password(user.getPassword())
				.authorities(new SimpleGrantedAuthority(user.getRole().name()))
				.build();
	}
}
