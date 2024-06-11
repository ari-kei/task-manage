package com.arikei.auth.domains;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.arikei.auth.domains.entities.AuthInfo;
import com.arikei.auth.domains.repositoryif.JwtPrivateKeyRepositoryIF;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtGenerator {

  private JwtPrivateKeyRepositoryIF jwtPrivateKeyRepositoryIF;

  // jwtの有効期限(単位：分)
  private final int EXPIRED_AT = 60;
  private final String BEGIN_RSA_PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----";
  private final String END_RSA_PRIVATE_KEY = "-----END RSA PRIVATE KEY-----";

  public JwtGenerator(JwtPrivateKeyRepositoryIF jwtPrivateKeyRepositoryIF) {
    this.jwtPrivateKeyRepositoryIF = jwtPrivateKeyRepositoryIF;
  }

  public String generateJwt(AuthInfo authInfo) {
    Optional<RSAPrivateKey> privatekeyOptional = getPrivateKey();
    if (privatekeyOptional.isEmpty()) {
      // TODO 鍵が取得できなかった際の処理
    }
    return JWT.create()
        .withIssuer("TaskManagerAuth")
        .withSubject(authInfo.getUserId())
        .withExpiresAt(OffsetDateTime.now().plusMinutes(this.EXPIRED_AT).toInstant())
        .withIssuedAt(OffsetDateTime.now().toInstant())
        .withJWTId(UUID.randomUUID().toString())
        .withClaim("role", authInfo.getRole())
        .sign(Algorithm.RSA256(privatekeyOptional.get()));
  }

  private Optional<RSAPrivateKey> getPrivateKey() {
    List<String> privateKeyLine = jwtPrivateKeyRepositoryIF.get();

    StringBuilder pemBuilder = new StringBuilder();
    for (String line : privateKeyLine) {
      if (this.BEGIN_RSA_PRIVATE_KEY.equals(line) || this.END_RSA_PRIVATE_KEY.equals(line)) {
        continue;
      }
      pemBuilder.append(line);
    }
    RSAPrivateKey rsaPrivateKey = null;
    try {
      rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
          .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(pemBuilder.toString())));
    } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return Optional.empty();
    }
    return Optional.of(rsaPrivateKey);
  }
}
