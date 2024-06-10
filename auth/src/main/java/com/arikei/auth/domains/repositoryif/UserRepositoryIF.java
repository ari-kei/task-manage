package com.arikei.auth.domains.repositoryif;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.arikei.auth.domains.entities.User;

@Component
public interface UserRepositoryIF {
  public Optional<User> findById(String userId);
}
