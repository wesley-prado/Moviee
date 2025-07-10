package com.codemages.Moviee.controllers.v1;

import com.codemages.Moviee.config.test.WebLayerTestConfig;
import com.codemages.Moviee.dtos.UserResponseDTO;
import com.codemages.Moviee.entities.Role;
import com.codemages.Moviee.entities.UserStatus;
import com.codemages.Moviee.security.AuthContextHelper;
import com.codemages.Moviee.services.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(WebLayerTestConfig.class)
class UserControllerTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @MockitoBean
  private UserService userServiceMock;

  @MockitoBean
  private AuthContextHelper authContextHelperMock;

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders
      .webAppContextSetup( context )
      .apply( springSecurity() )
      .build();
  }

  List<UserResponseDTO> users = List.of(
    new UserResponseDTO(
      UUID.randomUUID(), "Test User", "wesley@mail.com", Role.USER.name(),
      UserStatus.ACTIVE.name()
    ) );
  UUID userId = users.getFirst().id();

  @Test
  @WithMockUser(username = "admin", password = "Admin1#@", authorities = "ADMIN")
  void getUsers_whenUserHasAdminAuthority_shouldReturnSuccess()
    throws Exception {
    when( userServiceMock.findAll() ).thenReturn( users );

    mvc.perform( get( "/api/v1/users" ).accept( MediaTypes.HAL_JSON_VALUE )
        .secure( true ) )
      .andExpect( status().isOk() )
      .andExpect( content().contentType( MediaTypes.HAL_JSON_VALUE ) )
      .andExpect(
        jsonPath( "$._embedded.userResponseDTOes.size()", Matchers.is( 1 ) ) )
      .andExpect( jsonPath( "$._embedded.userResponseDTOes[0].id" )
        .value( userId.toString() ) )
      .andExpect( jsonPath( "$._embedded.userResponseDTOes[0].username" )
        .value( "Test User" ) )
      .andExpect( jsonPath( "$._embedded.userResponseDTOes[0].email" )
        .value( "wesley@mail.com" ) );

    verify( userServiceMock ).findAll();
  }

  @Test
  @WithMockUser(username = "user", password = "User1#@@", authorities = "USER")
  void getUsers_whenUserHasUserAuthority_shouldReturnForbidden()
    throws Exception {
    mvc.perform( get( "/api/v1/users" ).accept( MediaTypes.HAL_JSON_VALUE ).secure( true ) )
      .andExpect( status().isForbidden() );

    verify( userServiceMock, never() ).findAll();
  }
}