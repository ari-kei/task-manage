package com.arikei.auth.domains;

import java.util.Optional;

import com.arikei.auth.domains.entities.LoginInfo;
import com.arikei.auth.domains.entities.User;
import com.arikei.auth.domains.repositoryif.UserRepositoryIF;
import com.arikei.auth.domains.util.PasswordEncoder;

/**
 * ログイン情報を認証して、トークンを払い出す認証器
 */
public class Authenticator {

  // NOTE
  // https://github.com/ari-kei/task-manage/blob/main/auth/doc/designDoc/Driver_IntroduceRepositoryPattern.md
  private final UserRepositoryIF userRepositoryIF;
  private final PasswordEncoder passwordEncoder;

  // NOTE
  // https://github.com/ari-kei/task-manage/blob/main/auth/doc/designDoc/All_FieldInjection_ConstructorInjection.md
  public Authenticator(UserRepositoryIF userRepositoryIF, PasswordEncoder passwordEncoder) {
    this.userRepositoryIF = userRepositoryIF;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * ユーザのログイン認証に関する手続き
   * 
   * @param LoginInfo
   * @return JWT
   */
  // NOTE
  // https://github.com/ari-kei/task-manage/blob/main/auth/doc/designDoc/Domain_LoginInfo_Object_or_String.md
  public Optional<String> authenticate(LoginInfo LoginInfo) {
    Optional<User> user = this.userRepositoryIF.findById(LoginInfo.getUserId());
    if (!user.isPresent()) {
      // TODO 認証失敗(403)
    }

    if (!this.passwordEncoder.getPasswordEncoder().matches(LoginInfo.getPassword(), user.get().getHashedPassword())) {
      // TODO 認証失敗(403)
    }

    return generateToken(user.get());
  }

  /**
   * ユーザ認証情報をまとめてJWTを作成
   * 
   * @param user
   * @return JWT
   */
  Optional<String> generateToken(User user) {
    // TODO JWT作成
    return null;
  }
}
