package com.arikei.auth.domains.repositoryif;

public interface JwtKeyRepositoryIF {
  public String getPrivateKey();

  public String getPublicKey();
}
