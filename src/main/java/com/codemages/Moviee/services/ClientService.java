package com.codemages.Moviee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codemages.Moviee.dtos.ClientDTO;
import com.codemages.Moviee.entities.Client;
import com.codemages.Moviee.repositories.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {
	@Autowired
	private final ClientRepository clientRepository;
	@Autowired
	private final PasswordEncoder passwordEncoder;

	public void save(ClientDTO dto) {
		var client = new Client();
		client.setClientId(dto.clientId());
		client.setClientSecret(passwordEncoder.encode(dto.clientSecret()));
		client.setRedirectUri(dto.redirectUri());

		clientRepository.save(client);
	}

	public int count() {
		return (int) clientRepository.count();
	}
}
