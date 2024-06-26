package com.arikei.auth.drivers;

import java.util.Optional;
import com.arikei.auth.domains.PasswordEncoder;
import com.arikei.auth.domains.entities.User;
import com.arikei.auth.domains.repositoryif.UserRepositoryIF;

public class UserRepositoryImpl implements UserRepositoryIF {
  public Optional<User> findById(String userId) {
    // TODO データベースかファイルから取得できるようにする
    PasswordEncoder passwordEncoder = new PasswordEncoder();
    String hasedPassword = passwordEncoder.getPasswordEncoder().encode("password");
    User user = new User("1", "User Name", hasedPassword, "ADMIN");
    return Optional.of(user);
  }
}
