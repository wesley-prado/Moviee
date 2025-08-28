package com.codemages.Moviee.user.constant;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum Role {
  USER( "User", "Basic user role with limited access" ),
  ADMIN( "Admin", "Administrator with full access" ),
  MODERATOR( "Moderator", "Moderator with elevated privileges" );

  private final String displayName;
  private final String description;

  Role(String displayName, String description) {
    this.displayName = displayName;
    this.description = description;
  }

  public static List<String> getRoles() {
    List<String> roles = new ArrayList<>();

    for (Role role : Role.values()) {
      roles.add( role.displayName );
    }

    return roles;
  }
}
