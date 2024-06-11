package com.arikei.auth.domains;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.arikei.auth.domains.entities.AuthInfo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtGenerator {

  private Algorithm alg;
  // jwtの有効期限(単位：分)
  private final int EXPIRED_AT = 60;

  public JwtGenerator() {
    PrivateKey privateKey = getPrivateKey();
    // TODO 環境変数から秘密鍵の場所を取得して、読み込み
    this.alg = Algorithm.RSA256();
  }

  public String generateJwt(AuthInfo authInfo) {
    return JWT.create()
        .withIssuer("TaskManagerAuth")
        .withSubject(authInfo.getUserId())
        .withExpiresAt(OffsetDateTime.now().plusMinutes(this.EXPIRED_AT).toInstant())
        .withIssuedAt(OffsetDateTime.now().toInstant())
        .withJWTId(UUID.randomUUID().toString())
        .withClaim("role", authInfo.getRole())
        .sign(alg);
  }

  private PrivateKey getPrivateKey(Path path) {
    try (Stream<String> stream = Files.lines(path)) {
      // PEMの先頭と末尾を削除
      String pem = stream.filter(s -> !"-----BEGIN RSA PRIVATE KEY-----".equals(s)
          && !"-----END RSA PRIVATE KEY-----".equals(s)
          && !"\r\n".equals(s)).collect(Collectors.joining());

      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(pem));
      return KeyFactory.getInstance("RSA", new BouncyCastleProvider()).generatePrivate(keySpec);
    }
  }
}
