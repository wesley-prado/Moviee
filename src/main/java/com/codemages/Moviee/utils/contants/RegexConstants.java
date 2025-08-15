package com.codemages.Moviee.utils.contants;

public final class RegexConstants {
  private RegexConstants() {}

  public static final String URL_PATTERN = "^(http|https):\\/\\/[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}" +
    "(\\/\\S*)?$";
}
