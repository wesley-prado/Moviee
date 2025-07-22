package com.codemages.Moviee;

import com.codemages.Moviee.support.IntegrationTestContainerSingleton;
import org.junit.jupiter.api.Test;

class MovieeApplicationTests extends IntegrationTestContainerSingleton {
  @Test
  void contextLoads() {
    System.out.println( "Context loaded successfully!" );
  }
}
