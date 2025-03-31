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
	private static final String ROLE_PREFIX = "ROLE_";

	@Autowired
	private RoleRepository roleRepository;

	public RoleResponseDTO save(RoleCreateDTO dto) {
		Role entity = new Role();
		entity.setName(ROLE_PREFIX + dto.name().toUpperCase());
		entity.setStatus(RoleStatus.ACTIVE);

		entity = roleRepository.save(entity);

		return new RoleResponseDTO(entity.getId(), entity.getName());
	}

	public RoleResponseDTO findByName(String name) throws RoleException {
		String roleName = name.startsWith(ROLE_PREFIX) ? name
				: ROLE_PREFIX + name.toUpperCase();

		Role entity = roleRepository.findByName(roleName)
				.orElseThrow(() -> new RoleException("Role not found"));

		return new RoleResponseDTO(entity.getId(), entity.getName());
	}
}
