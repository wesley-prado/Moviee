package com.codemages.Moviee.auth.security.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PkceGenerator {
  public record Pkce(String verifier, String challenge) {}

  public static Pkce generate() {
    try {
      byte[] verifierBytes = new byte[ 32 ];
      new SecureRandom().nextBytes( verifierBytes );
      String codeVerifier = Base64.getUrlEncoder().withoutPadding().encodeToString( verifierBytes );

      MessageDigest sha256 = MessageDigest.getInstance( "SHA-256" );
      byte[] challengeBytes = sha256.digest( codeVerifier.getBytes() );
      String codeChallenge = Base64.getUrlEncoder()
        .withoutPadding()
        .encodeToString( challengeBytes );

      return new Pkce( codeVerifier, codeChallenge );
    } catch (NoSuchAlgorithmException e) {
      // Esta exceção nunca deve acontecer, pois SHA-256 é um requisito da JVM
      throw new RuntimeException( e );
    }
  }
}
