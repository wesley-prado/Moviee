package com.codemages.Moviee.controllers.v1;

import com.codemages.Moviee.models.LoginModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerTest {

  @Autowired
  private MockMvc mvc;

  @Test
  @DisplayName("Deve retornar a página de login com o modelo padrão")
  void login_shouldReturnLoginViewWithDefaultModel() throws Exception {
	mvc.perform( get( "/login" ) )
			.andExpect( status().isOk() )
			.andExpect( view().name( "login" ) )
			.andExpect( model().attributeExists( "loginModel" ) )
			.andExpect( model().attribute( "loginModel", instanceOf( LoginModel.class ) ) )
			.andExpect( model().attributeDoesNotExist( "error" ) )
			.andExpect( model().attributeDoesNotExist( "message" ) );
  }

  @Test
  @DisplayName("Deve retornar a página de login com mensagem de erro")
  void login_withError_shouldReturnLoginViewWithErrorMessage() throws Exception {
	mvc.perform( get( "/login" ).param( "error", "true" ) )
			.andExpect( status().isOk() )
			.andExpect( view().name( "login" ) )
			.andExpect( model().attributeExists( "loginModel" ) )
			.andExpect( model().attribute( "error", is( "Invalid username or password." ) ) )
			.andExpect( model().attributeDoesNotExist( "message" ) );
  }

  @Test
  @DisplayName("Deve retornar a página de login com mensagem de logout")
  void login_withLogout_shouldReturnLoginViewWithLogoutMessage() throws Exception {
	mvc.perform( get( "/login" ).param( "logout", "true" ) )
			.andExpect( status().isOk() )
			.andExpect( view().name( "login" ) )
			.andExpect( model().attributeExists( "loginModel" ) )
			.andExpect( model().attribute(
					"message",
					is( "You have been logged out successfully." )
			) )
			.andExpect( model().attributeDoesNotExist( "error" ) );
  }
}
