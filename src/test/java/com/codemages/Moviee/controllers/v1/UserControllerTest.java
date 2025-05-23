package com.codemages.moviee.controllers.v1;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.codemages.moviee.dtos.RoleResponseDTO;
import com.codemages.moviee.dtos.UserResponseDTO;
import com.codemages.moviee.entities.UserStatus;
import com.codemages.moviee.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
	private static final String DEFAULT_MEDIA_TYPE = "application/hal+json";
	private MockMvc mvc;
	@MockitoBean
	private UserService userService;
	@Autowired
	private WebApplicationContext context;

	private UUID userId = UUID.randomUUID();
	private UserResponseDTO userResponseDTO;
	private List<UserResponseDTO> userList;

	@BeforeEach
	void makeData() {
		userResponseDTO = new UserResponseDTO(userId, "Test User",
				"wesleyprado.dev@gmail.com", new RoleResponseDTO(1L, "ROLE_ADMIN"),
				UserStatus.ACTIVE.name());

		userList = List.of(userResponseDTO);

		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity())
				.build();
	}

	@Test
	@WithMockUser(username = "admin", password = "admin", authorities = "ROLE_ADMIN")
	void getUsersV1_whenUserHasAdminAuthority_shouldReturnSuccess()
			throws Exception {
		when(userService.findAll()).thenReturn(userList);

		mvc.perform(get("/api/v1/users").accept(DEFAULT_MEDIA_TYPE).secure(false))
				.andExpect(status().isOk())
				.andExpect(content().contentType(DEFAULT_MEDIA_TYPE))
				.andExpect(
						jsonPath("$._embedded.userResponseDTOes.size()", Matchers.is(1)))
				.andExpect(jsonPath("$._embedded.userResponseDTOes[0].id")
						.value(userId.toString()))
				.andExpect(jsonPath("$._embedded.userResponseDTOes[0].username")
						.value("Test User"))
				.andExpect(jsonPath("$._embedded.userResponseDTOes[0].email")
						.value("wesleyprado.dev@gmail.com"));

		verify(userService).findAll();
	}

	@Test
	@WithMockUser(authorities = "ROLE_USER")
	void getUsersV1_whenUserHasUserAuthority_shouldReturnForbidden()
			throws Exception {
		when(userService.findAll()).thenReturn(userList);

		mvc.perform(get("/api/v1/users").accept(DEFAULT_MEDIA_TYPE).secure(false))
				.andExpect(status().isForbidden());

		verify(userService, never()).findAll();
	}
}