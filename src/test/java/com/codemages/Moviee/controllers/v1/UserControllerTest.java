package com.codemages.Moviee.controllers.v1;

import com.codemages.Moviee.config.test.UserControllerTestConfig;
import com.codemages.Moviee.dtos.UserCreateDTO;
import com.codemages.Moviee.dtos.UserResponseDTO;
import com.codemages.Moviee.entities.Role;
import com.codemages.Moviee.exceptions.user.UserNotFoundException;
import com.codemages.Moviee.factories.UserFactory;
import com.codemages.Moviee.security.AuthContextHelper;
import com.codemages.Moviee.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(UserControllerTestConfig.class)
class UserControllerTest {
  private static final String USERS_ENDPOINT = "/api/v1/users";
  private final UserFactory userFactory = new UserFactory();
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private UserService userServiceMock;

  @MockitoBean
  private AuthContextHelper authContextHelperMock;

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup( context ).apply( springSecurity() ).build();
  }

  List<UserResponseDTO> users = List.of(
    userFactory.createValidUserResponseDTO( userFactory.createValidUserCreateDTO( Role.USER ) ),
    userFactory.createValidUserResponseDTO( userFactory.createValidUserCreateDTO( Role.MODERATOR ) ),
    userFactory.createValidUserResponseDTO( userFactory.createValidUserCreateDTO( Role.ADMIN ) )
  );
  private final int usersSize = users.size();

  @Nested
  @DisplayName("Testes para o método getUsers()")
  class GetUsersTest {

    @Test
    @DisplayName(
      "Quando um Admin chamar a API getUsers(), deve retornar status 200 e a lista de" +
        " " +
        "usuários")
    @WithMockUser(authorities = "ADMIN")
    void getUsers_whenUserHasAdminAuthority_shouldReturnSuccess() throws Exception {
      when( userServiceMock.findAll() ).thenReturn( users );

      var result = mvc.perform( get( getBaseUri() ).accept( MediaTypes.HAL_JSON_VALUE )
          .secure( true ) )
        .andDo( print() )
        .andExpect( status().isOk() )
        .andExpect( content().contentType( MediaTypes.HAL_JSON_VALUE ) )
        .andReturn();

      String jsonResponse = result.getResponse().getContentAsString();
      String jsonPathForUsersArray = "$._embedded.userResponseDTOes";
      List<Map<String, Object>> actualUsers = JsonPath.read( jsonResponse, jsonPathForUsersArray );
      assertThat( actualUsers ).hasSize( usersSize );

      for (UserResponseDTO expectedUser : users) {
        Optional<Map<String, Object>> actualUser = actualUsers.stream()
          .filter( user -> user.get( "id" ).equals( expectedUser.id().toString() ) )
          .findFirst();

        assertThat( actualUser ).isPresent();
        actualUser.ifPresent( user -> assertUserFields( user, expectedUser ) );
      }

      verify( userServiceMock ).findAll();
    }

    private void assertUserFields(Map<String, Object> user, UserResponseDTO expectedUser) {
      assertThat( user.get( "id" ) ).isEqualTo( expectedUser.id().toString() );
      assertThat( user.get( "username" ) ).isEqualTo( expectedUser.username() );
      assertThat( user.get( "email" ) ).isEqualTo( expectedUser.email() );
      assertThat( user.get( "role" ) ).isEqualTo( expectedUser.role() );
      assertThat( user.get( "status" ) ).isEqualTo( expectedUser.status() );
      assertThat( user.containsKey( "_links" ) ).isTrue();
    }

    @Test
    @DisplayName("Quando um Moderador chamar a API getUsers(), deve retornar status 403")
    @WithMockUser(authorities = "MODERATOR")
    void getUsers_whenUserHasModeratorAuthority_shouldReturnForbidden() throws Exception {
      mvc.perform( get( getBaseUri() ).accept( MediaTypes.HAL_JSON_VALUE ).secure( true ) )
        .andExpect( status().isForbidden() );

      verify( userServiceMock, never() ).findAll();
    }

    @Test
    @DisplayName("Quando um Usuário chamar a API getUsers(), deve retornar status 403")
    @WithMockUser(authorities = "USER")
    void getUsers_whenUserHasUserAuthority_shouldReturnForbidden() throws Exception {
      mvc.perform( get( getBaseUri() ).accept( MediaTypes.HAL_JSON_VALUE ).secure( true ) )
        .andExpect( status().isForbidden() );

      verify( userServiceMock, never() ).findAll();
    }
  }

  @Nested
  @DisplayName("Testes para o método getUser()")
  class GetUserTest {

    @Test
    @DisplayName(
      "Quando um Admin chamar a API getUser(), deve retornar status 200 e os dados do" +
        " " +
        "usuário")
    @WithMockUser(authorities = "ADMIN")
    void getUser_whenRunningUserIsAdmin_shouldReturnSuccess() throws Exception {
      UserResponseDTO user = users.getFirst();
      UUID userId = user.id();
      when( userServiceMock.findById( userId ) ).thenReturn( user );

      mvc.perform( get( getUserByIdUri( userId ) ).accept( MediaTypes.HAL_JSON_VALUE )
          .secure( true ) )
        .andExpect( status().isOk() )
        .andExpect( content().contentType( MediaTypes.HAL_JSON_VALUE ) )
        .andExpect( jsonPath( "$.id" ).value( userId.toString() ) )
        .andExpect( jsonPath( "$.username" ).value( user.username() ) )
        .andExpect( jsonPath( "$.email" ).value( user.email() ) )
        .andExpect( jsonPath( "$.role" ).value( user.role() ) )
        .andExpect( jsonPath( "$.status" ).value( user.status() ) );
    }

    @Test
    @DisplayName(
      "Quando um Moderador chamar a API getUser(), deve retornar status 200 e os dados do " +
        "usuário")
    @WithMockUser(authorities = "MODERATOR")
    void getUser_whenRunningUserIsModerator_shouldReturnSuccess() throws Exception {
      UserResponseDTO user = users.getFirst();
      UUID userId = user.id();
      when( userServiceMock.findById( userId ) ).thenReturn( user );

      mvc.perform( get( getUserByIdUri( userId ) ).accept( MediaTypes.HAL_JSON_VALUE )
          .secure( true ) )
        .andExpect( status().isOk() )
        .andExpect( content().contentType( MediaTypes.HAL_JSON_VALUE ) )
        .andExpect( jsonPath( "$.id" ).value( userId.toString() ) )
        .andExpect( jsonPath( "$.username" ).value( user.username() ) )
        .andExpect( jsonPath( "$.email" ).value( user.email() ) )
        .andExpect( jsonPath( "$.role" ).value( user.role() ) )
        .andExpect( jsonPath( "$.status" ).value( user.status() ) );
    }

    @Test
    @DisplayName("Quando um Moderador chamar a API getUser(), deve retornar status 404 e " +
      "mensagem de usuário não encontrado")
    @WithMockUser(authorities = "MODERATOR")
    void getUser_whenRunningUserIsModerator_UserIsNotFound_shouldReturnNotFound() throws Exception {
      UserResponseDTO user = users.getFirst();
      UUID userId = user.id();
      when( userServiceMock.findById( userId ) ).thenThrow( new UserNotFoundException(
        "User with ID " + userId + " not found." ) );

      mvc.perform( get( getUserByIdUri( userId ) ).accept( MediaTypes.HAL_JSON_VALUE )
          .secure( true ) )
        .andExpect( status().isNotFound() )
        .andExpect( content().contentType( MediaTypes.HAL_JSON_VALUE ) )
        .andExpect( jsonPath( "$.status" ).value( HttpStatus.NOT_FOUND.value() ) )
        .andExpect( jsonPath( "$.message" ).value(
          "User with ID " + userId + " not found." ) )
        .andExpect( jsonPath( "$.timestamp" ).exists() )
        .andExpect( jsonPath( "$.errors" ).doesNotExist() )
        .andExpect( jsonPath( "$._links.about.href" ).value(
          "https://my-api-docs.com/errors/user-not-found" ) );
    }

    @Test
    @DisplayName("Quando um Usuário chamar a API getUser(), deve retornar status 403")
    @WithMockUser(authorities = "USER")
    void getUser_whenRunningUserIsUser_shouldReturnForbidden() throws Exception {
      UserResponseDTO user = users.getFirst();
      UUID userId = user.id();
      when( userServiceMock.findById( userId ) ).thenReturn( users.getFirst() );

      mvc.perform( get( getUserByIdUri( userId ) ).accept( MediaTypes.HAL_JSON_VALUE )
        .secure( true ) ).andExpect( status().isForbidden() );
    }
  }

  @Nested
  @DisplayName("Testes para o método createUser()")
  class CreateUserTest {
    @Nested
    @DisplayName("Usuário Admin")
    class AdminTests {
      @Test
      @DisplayName(
        "Quando tentar criar uma conta do tipo User, deve retornar status 201 e os dados " +
          "do" + " " + "usuário criado")
      @WithMockUser(authorities = "ADMIN")
      void createUser_whenRunningUserIsAdmin_TypeOfUserBeingCreatedIsUser_shouldReturnSuccess() throws
        Exception {
        UserCreateDTO dto = userFactory.createValidUserCreateDTO( Role.USER );
        UserResponseDTO createdUser = userFactory.createValidUserResponseDTO( dto );
        when( userServiceMock.createUser( dto ) ).thenReturn( createdUser );

        var result = mvc.perform( post( USERS_ENDPOINT ).content( objectMapper.writeValueAsString(
            dto ) )
          .contentType( "application/json" )
          .accept( MediaTypes.HAL_JSON_VALUE )
          .secure( true )
          .with( csrf() ) );

        assertIsCreated( result, createdUser );
      }

      @Test
      @DisplayName(
        "Quando tentar criar uma conta do tipo Moderator, deve retornar status 201 e os " +
          "dados do " + "usuário criado")
      @WithMockUser(authorities = "ADMIN")
      void createUser_whenRunningUserIsAdmin_TypeOfUserBeingCreatedIsModerator_shouldReturnSuccess() throws
        Exception {
        UserCreateDTO dto = userFactory.createValidUserCreateDTO( Role.MODERATOR );
        UserResponseDTO createdUser = userFactory.createValidUserResponseDTO( dto );
        when( userServiceMock.createUser( dto ) ).thenReturn( createdUser );
        when( authContextHelperMock.isUserAdmin() ).thenReturn( true );

        var result = mvc.perform( post( USERS_ENDPOINT ).content( objectMapper.writeValueAsString(
            dto ) )
          .contentType( "application/json" )
          .accept( MediaTypes.HAL_JSON_VALUE )
          .secure( true )
          .with( csrf() ) );

        assertIsCreated( result, createdUser );
      }

      @Test
      @DisplayName(
        "Quando tentar criar uma conta do tipo Moderator, deve retornar status 201 e os " +
          "dados do " + "usuário criado")
      @WithMockUser(authorities = "ADMIN")
      void createUser_whenRunningUserIsAdmin_TypeOfUserBeingCreatedIsAdmin_shouldReturnSuccess() throws
        Exception {
        UserCreateDTO dto = userFactory.createValidUserCreateDTO( Role.ADMIN );
        UserResponseDTO createdUser = userFactory.createValidUserResponseDTO( dto );
        when( userServiceMock.createUser( dto ) ).thenReturn( createdUser );
        when( authContextHelperMock.isUserAdmin() ).thenReturn( true );

        var result = mvc.perform( post( USERS_ENDPOINT ).content( objectMapper.writeValueAsString(
            dto ) )
          .contentType( "application/json" )
          .accept( MediaTypes.HAL_JSON_VALUE )
          .secure( true )
          .with( csrf() ) );

        assertIsCreated( result, createdUser );
      }
    }

    @ParameterizedTest
    @CsvSource({
      "MODERATOR, MODERATOR",  // Moderador tentando criar Moderador -> Proibido
      "MODERATOR, ADMIN",  // Moderador tentando criar Admin    -> Proibido
      "USER,      MODERATOR",  // User tentando criar Moderador      -> Proibido
      "USER,      ADMIN"   // User tentando criar Admin         -> Proibido
    })
    @DisplayName("Quando um usuário não-admin tenta criar roles especiais, deve retornar status " +
      "403")
    void createUser_whenNonAdminTriesToCreateSpecialRoles_shouldReturnForbidden(
      Role runningUserRole,
      Role roleToCreate
    ) throws Exception {
      UserCreateDTO dto = userFactory.createValidUserCreateDTO( roleToCreate );
      when( authContextHelperMock.isUserAdmin() ).thenReturn( false );

      var result = mvc.perform( post( USERS_ENDPOINT ).with( user( runningUserRole.name() ) ) //
        // Simula o usuário logado
        .content( objectMapper.writeValueAsString( dto ) )
        .contentType( "application/json" )
        .accept( MediaTypes.HAL_JSON_VALUE )
        .secure( true )
        .with( csrf() ) );

      assertIsForbidden( result );
      verify( userServiceMock, never() ).createUser( any() );
    }

    @Nested
    @DisplayName("Teste de exceção")
    class ExceptionTests {
      @Test
      @DisplayName(
        "Quando o usuário tentar criar uma conta com dados inválidos, deve retornar status" +
          " " + "400 e um array de erros")
      @WithMockUser(authorities = "ADMIN")
      void createUser_whenUserDataIsInvalid_shouldReturnBadRequestWithErrorsArray() throws
        Exception {
        UserCreateDTO dto = userFactory.createValidUserCreateDTO( Role.USER );
        dto = new UserCreateDTO(
          dto.username(),
          dto.email(),
          "short",
          dto.document(),
          dto.documentType(),
          dto.role()
        );

        mvc.perform( post( USERS_ENDPOINT ).content( objectMapper.writeValueAsString( dto ) )
            .contentType( "application/json" )
            .accept( MediaTypes.HAL_JSON_VALUE )
            .secure( true )
            .with( csrf() ) )
          .andExpect( status().isBadRequest() )
          .andExpect( content().contentType( MediaTypes.HAL_JSON_VALUE ) )
          .andExpect( jsonPath( "$.status" ).value( HttpStatus.BAD_REQUEST.value() ) )
          .andExpect( jsonPath( "$.message" ).value( "Field validation error" ) )
          .andExpect( jsonPath( "$.timestamp" ).exists() )
          .andExpect( jsonPath( "$.errors.length()", Matchers.is( 1 ) ) )
          .andExpect( jsonPath( "$.errors[0]" ).value(
            "password: Password must be between 8 and 20 characters long, contain at" +
              " " +
              "least one uppercase letter, one lowercase letter, one digit, " +
              "and" + " one special character." ) )
          .andExpect( jsonPath( "$._links.about.href" ).value(
            "https://my-api-docs.com/errors/validations" ) );

        verifyNoInteractions( userServiceMock );
      }

      //TODO: Mover este teste para o UserExceptionHandlerTest
      @Test
      @DisplayName(
        "Quando o usuário tentar criar uma conta com dados inválidos, deve retornar status" +
          " " + "400 e ser tratado pelo UserExceptionHandler")
      @WithMockUser(authorities = "ADMIN")
      void createUser_whenUserDataIsInvalid_shouldBeHandledByUserExceptionHandler() throws
        Exception {
        UserCreateDTO dto = userFactory.createValidUserCreateDTO( Role.USER );
        when( userServiceMock.createUser( dto ) ).thenThrow( new DataIntegrityViolationException(
          "Some database exception" ) );

        mvc.perform( post( USERS_ENDPOINT ).content( objectMapper.writeValueAsString( dto ) )
            .contentType( "application/json" )
            .accept( MediaTypes.HAL_JSON_VALUE )
            .secure( true )
            .with( csrf() ) )
          .andExpect( status().isBadRequest() )
          .andExpect( content().contentType( MediaTypes.HAL_JSON_VALUE ) )
          .andExpect( jsonPath( "$.status" ).value( HttpStatus.BAD_REQUEST.value() ) )
          .andExpect( jsonPath( "$.message" ).value(
            "Data integrity violation: Some database exception" ) )
          .andExpect( jsonPath( "$.timestamp" ).exists() )
          .andExpect( jsonPath( "$.errors" ).doesNotExist() )
          .andExpect( jsonPath( "$._links.about.href" ).value(
            "https://my-api-docs.com/errors/data-integrity-violation" ) );
      }

    }

    void assertIsCreated(ResultActions result, UserResponseDTO userResponseDTO) throws Exception {
      result.andExpect( status().isCreated() )
        .andExpect( content().contentType( MediaTypes.HAL_JSON_VALUE ) )
        .andExpect( jsonPath( "$.id" ).value( userResponseDTO.id().toString() ) )
        .andExpect( jsonPath( "$.username" ).value( userResponseDTO.username() ) )
        .andExpect( jsonPath( "$.email" ).value( userResponseDTO.email() ) )
        .andExpect( jsonPath( "$.role" ).value( userResponseDTO.role() ) )
        .andExpect( jsonPath( "$.status" ).value( userResponseDTO.status() ) );
    }

    void assertIsForbidden(ResultActions result) throws Exception {
      result.andExpect( status().isForbidden() )
        .andExpect( content().contentType( MediaTypes.HAL_JSON_VALUE ) )
        .andExpect( jsonPath( "$.status" ).value( HttpStatus.FORBIDDEN.value() ) )
        .andExpect( jsonPath( "$.message" ).value(
          "Only admins can create other admins or moderators." ) )
        .andExpect( jsonPath( "$.timestamp" ).exists() )
        .andExpect( jsonPath( "$.errors" ).doesNotExist() )
        .andExpect( jsonPath( "$._links.about.href" ).value(
          "https://my-api-docs.com/errors/forbidden" ) );
    }
  }

  private static URI getBaseUri() {
    return UriComponentsBuilder.fromPath( USERS_ENDPOINT )
      .build()
      .toUri();
  }

  private static URI getUserByIdUri(UUID userId) {
    return UriComponentsBuilder.fromPath( USERS_ENDPOINT + "/{id}" )
      .buildAndExpand( userId )
      .toUri();
  }
}
