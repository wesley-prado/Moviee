package com.codemages.Moviee.config.test;

import com.codemages.Moviee.assemblers.UserModelAssembler;
import com.codemages.Moviee.config.RoleHierarchyConfig;
import com.codemages.Moviee.security.AuthContextHelper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({
  UserModelAssembler.class,
  RoleHierarchyConfig.class
})
public class WebLayerTestConfig {
}
