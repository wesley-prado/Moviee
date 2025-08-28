package com.codemages.Moviee.user.controller.v1;

import com.codemages.Moviee.user.config.RoleControllerTestConfig;
import com.codemages.Moviee.user.constant.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PrivateRoleController.class)
@Import(RoleControllerTestConfig.class)
public class PrivateRoleControllerTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @BeforeEach
  public void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup( context ).apply( springSecurity() )
      .build();
  }

  @ParameterizedTest
  @CsvSource({
    "ADMIN",
    "MODERATOR",
    "USER"
  })
  @DisplayName(
    "Quando um usuário logado acessar GET /api/v1/roles, deve retornar 200 e uma lista" +
      " de roles")
  public void getRoles_whenNonStandardUser(Role role) throws Exception {
    mvc.perform( get( getRolesURI() ).with( user( role.name() ) )
        .accept( MediaTypes.HAL_JSON ) )
      .andExpect( status().isOk() )
      .andExpect( content().contentType( MediaTypes.HAL_JSON ) )
      .andExpect( jsonPath( "$._embedded.roles", hasSize( Role.values().length ) ) );
  }

  @Test
  @DisplayName("Quando um usuário não logado acessar GET /api/v1/roles, deve retornar 302" +
    " e redirecionar para a página de login")

  public void getRoles_whenStandardUser() throws Exception {
    mvc.perform( get( getRolesURI() ).with( anonymous() )
        .accept( MediaTypes.HAL_JSON ) )
      .andExpect( status().isFound() )
      .andExpect( redirectedUrlPattern( "**/login" ) );
  }

  static URI getRolesURI() {
    return URI.create( "/api/v1/roles" );
  }
}
