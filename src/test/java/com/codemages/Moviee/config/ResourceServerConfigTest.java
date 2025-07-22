package com.codemages.Moviee.config;

import com.codemages.Moviee.entities.DocumentType;
import com.codemages.Moviee.entities.Role;
import com.codemages.Moviee.entities.User;
import com.codemages.Moviee.repositories.UserRepository;
import com.codemages.Moviee.support.IntegrationTestContainerSingleton;
import com.codemages.Moviee.utils.password.interfaces.PasswordGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ResourceServerConfigTest extends IntegrationTestContainerSingleton {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private PasswordGenerator passwordGenerator;

  @Test
  @DisplayName("Deve permitir acesso à página de login para qualquer usuário")
  void loginPage_shouldBePublic() throws Exception {
    mvc.perform( get( "/login" ) ).andExpect( status().isOk() );
  }

  @Test
  @DisplayName("Deve bloquear acesso a endpoints protegidos para usuários anônimos")
  void protectedEndpoints_shouldBeBlockedForAnonymous() throws Exception {
    mvc.perform( get( "/explorer/" ) )
      .andExpect( status().isUnauthorized() );
  }

  @Test
  @DisplayName("Deve negar acesso ao /explorer para usuário com role USER")
  @WithMockUser(username = "user", authorities = { "USER" })
  void explorer_shouldBeForbiddenForUserRole() throws Exception {
    mvc.perform( get( "/explorer/" ) ).andExpect( status().isForbidden() );
  }

  @Test
  @DisplayName("Deve permitir acesso ao /explorer para usuário com role ADMIN")
  @WithMockUser(username = "admin", authorities = { "ADMIN" })
  void explorer_shouldBeAllowedForAdminRole() throws Exception {
    mvc.perform( get( "/explorer/index.html#uri=/" ) ).andExpect( status().isOk() );
  }

  @Test
  @DisplayName("Deve realizar logout e redirecionar para a página de login")
  @WithMockUser
  void logout_shouldRedirectToLogin() throws Exception {
    mvc.perform( post( "/logout" ).with( csrf() ) )
      .andExpect( status().is3xxRedirection() )
      .andExpect( redirectedUrl( "/login" ) );
  }

  @Test
  @Transactional
  @DisplayName("Deve autenticar via cookie remember-me após login inicial")
  void rememberMe_shouldAuthenticateUserOnNewSesssion() throws Exception {
    String pass = passwordGenerator.generate();
    var userEntity = new User(
      null,
      "admin",
      "any_email@mail.com",
      passwordEncoder.encode( pass ),
      Role.ADMIN,
      "228514939",
      DocumentType.RG
    );
    var newUser = userRepository.save( userEntity );

    assertThat( newUser.getId() ).isNotNull();

    MvcResult result = mvc.perform( post( "/login" )
        .param( "username", userEntity.getUsername() )
        .param( "password", pass )
        .param( "remember-me", "true" )
        .with( csrf() ) )
      .andExpect( authenticated() )
      .andExpect( cookie().exists( "remember-me" ) )
      .andReturn();

    MockHttpSession sessionWithCookie = (MockHttpSession) result.getRequest().getSession();

    assertThat( sessionWithCookie ).isNotNull();

    mvc.perform( get( "/api/v1/users" ).session( sessionWithCookie ) )
      .andExpect( authenticated().withAuthenticationName( "admin" ) )
      .andExpect( status().isOk() );
  }
}
