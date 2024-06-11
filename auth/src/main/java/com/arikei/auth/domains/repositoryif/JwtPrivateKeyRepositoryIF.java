package com.arikei.auth.domains.repositoryif;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface JwtPrivateKeyRepositoryIF {
  public List<String> get();
}
