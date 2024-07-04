package com.arikei.auth.domains;

import java.util.Optional;

import com.arikei.auth.domains.entities.AuthInfo;
import com.arikei.auth.domains.entities.LoginInfo;
import com.arikei.auth.domains.entities.User;
import com.arikei.auth.domains.repositoryif.UserRepositoryIF;

/**
 * ログイン情報を認証して、トークンを払い出す認証器
 */
public class Authenticator {

  // NOTE
  // https://github.com/ari-kei/task-manage/blob/main/auth/doc/designDoc/Driver_IntroduceRepositoryPattern.md
  private final UserRepositoryIF userRepositoryIF;
  private final PasswordEncoder passwordEncoder;
  private final JwtGenerator jwtGenerator;

  // NOTE
  // https://github.com/ari-kei/task-manage/blob/main/auth/doc/designDoc/All_FieldInjection_ConstructorInjection.md
  public Authenticator(UserRepositoryIF urIF, PasswordEncoder pe, JwtGenerator jg) {
    this.userRepositoryIF = urIF;
    this.passwordEncoder = pe;
    this.jwtGenerator = jg;
  }

  /**
   * ユーザのログイン認証に関する手続き
   * 
   * @param LoginInfo
   * @return JWT
   */
  // NOTE
  // https://github.com/ari-kei/task-manage/blob/main/auth/doc/designDoc/Domain_LoginInfo_Object_or_String.md
  public String authenticate(LoginInfo LoginInfo) throws IllegalArgumentException {
    Optional<User> user = this.userRepositoryIF.findById(LoginInfo.userId());
    if (!user.isPresent()) {
      System.out.println("User does not exist");
      throw new IllegalArgumentException();
    }

    if (!this.passwordEncoder.getPasswordEncoder().matches(LoginInfo.password(),
        user.get().getHashedPassword())) {
      System.out.println("Password does not match");
      throw new IllegalArgumentException();
    }

    return this.jwtGenerator.generateJwt(new AuthInfo(user.get().getUserId()));
  }
}
