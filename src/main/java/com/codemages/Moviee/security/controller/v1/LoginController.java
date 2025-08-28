package com.codemages.Moviee.security.controller.v1;

import com.codemages.Moviee.security.config.constants.ApiPaths;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SuppressWarnings("SameReturnValue")
@Controller
public class LoginController {
  @GetMapping(ApiPaths.LOGIN)
  public String login(
    Model model, @RequestParam(required = false) String error,
    @RequestParam(required = false) String logout
  ) {
    if ( error != null ) {

      model.addAttribute( "error", "Invalid username or password." );
    }
    if ( logout != null ) {
      model.addAttribute( "logout", "You have been logged out successfully." );
    }

    return "login";
  }
}