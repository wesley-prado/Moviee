package com.codemages.Moviee.user.config;

import com.codemages.Moviee.auth.security.config.RoleHierarchyConfig;
import com.codemages.Moviee.auth.security.password.PasswordPolicyValidator;
import com.codemages.Moviee.user.assembler.UserModelAssembler;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({
  UserModelAssembler.class,
  RoleHierarchyConfig.class,
  PasswordPolicyValidator.class
})
public class UserControllerTestConfig {
}
