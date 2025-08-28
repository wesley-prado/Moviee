package com.codemages.Moviee.client;

import com.codemages.Moviee.client.dto.ClientDTO;
import com.codemages.Moviee.client.exception.ClientIdAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {
  private final ClientRepository clientRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void save(ClientDTO dto) {
    clientRepository.findByClientId( dto.clientId() ).ifPresent( client -> {
      throw new ClientIdAlreadyExistsException(
        "Client ID '" + dto.clientId() + "' already exists." );
    } );

    var client = new Client();
    client.setClientId( dto.clientId() );
    client.setClientSecret( passwordEncoder.encode( dto.clientSecret() ) );
    client.setRedirectUri( dto.redirectUri() );
    client.setClientName( dto.clientName() );

    clientRepository.save( client );
  }

  @Transactional(readOnly = true)
  public boolean existsByClientId(String clientId) {
    return clientRepository.existsByClientId( clientId );
  }
}
