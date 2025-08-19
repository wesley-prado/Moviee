package com.codemages.Moviee.security.controllers.v1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerTest {
  private static final String LOGIN_URL = "/auth/login";

  @Autowired
  private MockMvc mvc;

  @Test
  @DisplayName("Deve retornar a página de login com o modelo padrão")
  void login_shouldReturnLoginViewWithDefaultModel() throws Exception {
    mvc.perform( get( LOGIN_URL ) )
      .andExpect( status().isOk() )
      .andExpect( view().name( "login" ) )
      .andExpect( model().attributeDoesNotExist( "error" ) )
      .andExpect( model().attributeDoesNotExist( "logout" ) );
  }

  @Test
  @DisplayName("Deve retornar a página de login com mensagem de erro")
  void login_withError_shouldReturnLoginViewWithErrorMessage() throws Exception {
    mvc.perform( get( LOGIN_URL ).param( "error", "true" ) )
      .andExpect( status().isOk() )
      .andExpect( view().name( "login" ) )
      .andExpect( model().attribute( "error", is( "Invalid username or password." ) ) )
      .andExpect( model().attributeDoesNotExist( "logout" ) );
  }

  @Test
  @DisplayName("Deve retornar a página de login com mensagem de logout")
  void login_withLogout_shouldReturnLoginViewWithLogoutMessage() throws Exception {
    mvc.perform( get( LOGIN_URL ).param( "logout", "true" ) )
      .andExpect( status().isOk() )
      .andExpect( view().name( "login" ) )
      .andExpect( model().attribute(
        "logout",
        is( "You have been logged out successfully." )
      ) )
      .andExpect( model().attributeDoesNotExist( "error" ) );
  }
}
