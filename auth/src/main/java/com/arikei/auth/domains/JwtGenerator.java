package com.arikei.auth.domains;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.arikei.auth.domains.entities.AuthInfo;
import com.arikei.auth.domains.repositoryif.JwtKeyRepositoryIF;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtGenerator {

  private JwtKeyRepositoryIF jwtKeyRepositoryIF;
  private Algorithm alg;

  // jwtの有効期限(単位：分)
  private final String ISSUER = "TaskManagerAuth";
  private final int EXPIRED_AT = 60;
  private final String PRIVATE_MAP_KEY = "private";
  private final String PUBLIC_MAP_KEY = "public";

  public JwtGenerator(JwtKeyRepositoryIF jwtKeyRepositoryIF) {
    this.jwtKeyRepositoryIF = jwtKeyRepositoryIF;

    Optional<Map<String, RSAKey>> keyMapOptional = getKeyPair();
    if (keyMapOptional.isEmpty()) {
      // TODO 鍵が取得できなかった際の処理
    }
    this.alg = Algorithm.RSA256((RSAPublicKey) keyMapOptional.get().get(PUBLIC_MAP_KEY),
        (RSAPrivateKey) keyMapOptional.get().get(PRIVATE_MAP_KEY));
  }

  /**
   * JWTを生成する
   * 
   * @param authInfo
   * @return
   */
  public String generateJwt(AuthInfo authInfo) {
    return JWT.create().withIssuer(this.ISSUER).withSubject(authInfo.userId())
        .withExpiresAt(OffsetDateTime.now().plusMinutes(this.EXPIRED_AT).toInstant())
        .withIssuedAt(OffsetDateTime.now().toInstant()).withJWTId(UUID.randomUUID().toString())
        .sign(this.alg);
  }

  public DecodedJWT verifyToken(String token) {
    JWTVerifier verifier = JWT.require(this.alg).withIssuer(this.ISSUER).acceptExpiresAt(5).build();
    try {
      return verifier.verify(token);
    } catch (JWTVerificationException e) {
      System.out.println("JWT verification failed..");
      throw e;
    }
  }

  private Optional<Map<String, RSAKey>> getKeyPair() {
    // 秘密鍵取得
    String privateKey = jwtKeyRepositoryIF.getPrivateKey();
    String privateKeyTrimmed =
        privateKey.replaceAll("-----.+?-----", "").replaceAll("\n", "").replaceAll("\r", "").trim();
    // 公開鍵取得
    String publicKey = jwtKeyRepositoryIF.getPublicKey();
    String publicKeyTrimmed =
        publicKey.replaceAll("-----.+?-----", "").replaceAll("\n", "").replaceAll("\r", "").trim();
    RSAPrivateKey rsaPrivateKey = null;
    RSAPublicKey rsaPublicKey = null;
    try {
      rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
          .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyTrimmed)));

      rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
          .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyTrimmed)));
    } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
      e.printStackTrace();
      return Optional.empty();
    }

    Map<String, RSAKey> keyMap = new HashMap<>();
    keyMap.put(PRIVATE_MAP_KEY, rsaPrivateKey);
    keyMap.put(PUBLIC_MAP_KEY, rsaPublicKey);
    return Optional.of(keyMap);
  }
}
