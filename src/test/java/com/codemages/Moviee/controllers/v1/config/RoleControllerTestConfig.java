package com.codemages.Moviee.controllers.v1.config;

import com.codemages.Moviee.assemblers.RoleModelAssembler;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(RoleModelAssembler.class)
public class RoleControllerTestConfig {
}
