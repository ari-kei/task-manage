package com.arikei.auth.domains.repositoryif;

import java.util.Optional;

import com.arikei.auth.domains.entities.User;

public interface UserRepositoryIF {
  public Optional<User> findById(String userId);
}
