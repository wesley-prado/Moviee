package com.codemages.moviee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemages.moviee.dtos.RoleCreateDTO;
import com.codemages.moviee.dtos.RoleResponseDTO;
import com.codemages.moviee.entities.Role;
import com.codemages.moviee.entities.RoleStatus;
import com.codemages.moviee.exceptions.RoleException;
import com.codemages.moviee.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
  @Autowired
  private RoleRepository roleRepository;

  public RoleResponseDTO save(RoleCreateDTO dto) {
    Role entity = new Role();
    entity.setName(dto.name());
    entity.setStatus(RoleStatus.ACTIVE);

    entity = roleRepository.save(entity);

    return new RoleResponseDTO(entity.getId(), entity.getName());
  }

  public RoleResponseDTO findByName(String name) throws RoleException {
    Role entity = roleRepository.findByName(name)
        .orElseThrow(() -> new RoleException("Role not found"));

    return new RoleResponseDTO(entity.getId(), entity.getName());
  }
}
