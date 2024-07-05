package com.arikei.auth.domains;

import java.util.Optional;
import com.arikei.auth.domains.entities.User;
import com.arikei.auth.domains.entities.VerifyInfo;
import com.arikei.auth.domains.repositoryif.UserRepositoryIF;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * 認証トークンを受け取って、認証情報を返却する検証機
 */
public class Verifier {

  private final JwtGenerator jwtGenerator;
  private final UserRepositoryIF userRepositoryIF;

  public Verifier(JwtGenerator jg, UserRepositoryIF urIf) {
    this.jwtGenerator = jg;
    this.userRepositoryIF = urIf;
  }

  public User verify(VerifyInfo verifyInfo) throws IllegalArgumentException {
    DecodedJWT decodedJWT = null;
    try {
      decodedJWT = jwtGenerator.verifyToken(verifyInfo.token());
    } catch (JWTDecodeException e) {
      System.out.println("fail to decode JWT");
      throw new IllegalArgumentException();
    }
    String userId = decodedJWT.getIssuer();
    Optional<User> userOpt = userRepositoryIF.findById(userId);
    if (userOpt.isEmpty()) {
      System.out.println("cannot find user");
      throw new IllegalArgumentException();
    }
    return userOpt.get();
  }
}
