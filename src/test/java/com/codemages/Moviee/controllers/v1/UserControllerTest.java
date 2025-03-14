package com.codemages.moviee.controllers.v1;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.codemages.moviee.assemblers.UserModelAssembler;
import com.codemages.moviee.dtos.RoleResponseDTO;
import com.codemages.moviee.dtos.UserResponseDTO;
import com.codemages.moviee.entities.UserStatus;
import com.codemages.moviee.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    private static final String DEFAULT_MEDIA_TYPE = "application/hal+json";
    private MockMvc mvc;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private UserModelAssembler userModelAssembler;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;

    private UUID userId = UUID.randomUUID();
    private UserResponseDTO userResponseDTO;
    private List<UserResponseDTO> userList;

    @BeforeEach
    void makeData() {
        userResponseDTO = new UserResponseDTO(userId, "Test User",
                "wesleyprado.dev@gmail.com", new RoleResponseDTO(1L, "ADMIN"),
                UserStatus.ACTIVE.name());

        userList = List.of(userResponseDTO);

        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity()).build();
    }

    @Test
    void testGetUsersV1() throws Exception {
        // Arrange
        when(userService.findAll()).thenReturn(userList);

        // Act & Assert
        mvc.perform(
                get("/api/v1/users").accept(DEFAULT_MEDIA_TYPE).secure(false))
                .andExpect(status().isOk())
                .andExpect(content().contentType(DEFAULT_MEDIA_TYPE));

        verify(userService).findAll();
        verify(userModelAssembler).toCollectionModel(userList);
    }
}