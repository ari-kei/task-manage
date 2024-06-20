package com.arikei.auth.domains.repositoryif;

import org.springframework.stereotype.Component;

@Component
public interface JwtKeyRepositoryIF {
  public String getPrivateKey();

  public String getPublicKey();
}
