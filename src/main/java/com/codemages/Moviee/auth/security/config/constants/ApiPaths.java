package com.codemages.Moviee.auth.security.config.constants;

public class ApiPaths {
  public static final String LOGIN = "/login";
  public static final String LOGOUT = "/logout";
  public static final String CONSENT = "/oauth2/consent";
  public static final String CSS = "/css/**";
  public static final String JS = "/js/**";
  public static final String ERROR = "/error/**";

  public static final String[] PUBLIC_RESOURCES = { "/api/public/**" };
  public static final String[] PRIVATE_RESOURCES = { "/api/**" };
  public static final String[] EXPLORER_RESOURCES = { "/explorer/**" };
  public static final String[] UI_RESOURCES = { LOGIN, LOGOUT, CONSENT, CSS, JS, ERROR };
}
