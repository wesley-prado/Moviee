package com.codemages.Moviee.user.config;

import com.codemages.Moviee.user.assemblers.RoleModelAssembler;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(RoleModelAssembler.class)
public class RoleControllerTestConfig {
}
