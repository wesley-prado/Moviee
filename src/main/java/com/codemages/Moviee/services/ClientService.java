package com.codemages.Moviee.services;

import com.codemages.Moviee.dtos.ClientDTO;
import com.codemages.Moviee.entities.Client;
import com.codemages.Moviee.exceptions.client.ClientNotFoundException;
import com.codemages.Moviee.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {
  private final ClientRepository clientRepository;

  @Transactional
  public void save(ClientDTO dto) {
    var client = new Client();
    client.setClientId( dto.clientId() );
    client.setRedirectUri( dto.redirectUri() );
    client.setClientName( dto.clientName() );

    clientRepository.save( client );
  }

  @Transactional
  public void update(ClientDTO dto) throws ClientNotFoundException {
    var client = clientRepository.findByClientId( dto.clientId() );

    if ( client == null ) {
      throw new ClientNotFoundException( "Client with ID " + dto.clientId() + " does not exist." );
    }

    client.setRedirectUri( dto.redirectUri() );
    clientRepository.save( client );
  }

  public int count() {
    return (int) clientRepository.count();
  }
}
