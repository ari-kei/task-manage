package com.arikei.auth.domains;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.arikei.auth.domains.entities.AuthInfo;
import com.arikei.auth.domains.repositoryif.JwtKeyRepositoryIF;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * JWT生成のテスト JWT生成→JWT検証で成功することをテストする
 */
public class JwtGeneratorTest {
  private static String privateKey = "";
  private static String publicKey = "";
  private static final String BEGIN_RSA_PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----";
  private static final String END_RSA_PRIVATE_KEY = "-----END RSA PRIVATE KEY-----";
  private static final String BEGIN_RSA_PUBLIC_KEY = "-----BEGIN RSA PUBLIC KEY-----";
  private static final String END_RSA_PUBLIC_KEY = "-----END RSA PUBLIC KEY-----";

  @BeforeAll
  static void setup() {
    // キーペア
    KeyPairGenerator keyPairGenerator = null;
    try {
      keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    } catch (Exception e) {
      System.err.println(e);
    }
    keyPairGenerator.initialize(2048);
    KeyPair keyPair = keyPairGenerator.genKeyPair();

    byte[] publicBytes = keyPair.getPublic().getEncoded();
    publicKey =
        BEGIN_RSA_PUBLIC_KEY + Base64.getEncoder().encodeToString(publicBytes) + END_RSA_PUBLIC_KEY;
    byte[] privateBytes = keyPair.getPrivate().getEncoded();
    privateKey = BEGIN_RSA_PRIVATE_KEY + Base64.getEncoder().encodeToString(privateBytes)
        + END_RSA_PRIVATE_KEY;
  }

  public class JwtKeyRepositoryMock implements JwtKeyRepositoryIF {
    public String getPrivateKey() {
      return privateKey;
    }

    public String getPublicKey() {
      return publicKey;
    }
  }

  @Test
  void testGenerateJwt() {
    // 事前準備
    AuthInfo authInfo = new AuthInfo("user_id_test", "role_test");

    JwtGenerator jwtGenerator = new JwtGenerator(new JwtKeyRepositoryMock());

    // 実行
    String token = jwtGenerator.generateJwt(authInfo);

    // 検証
    if (token.isEmpty()) {
      fail();
    }

    DecodedJWT decodedJWT = jwtGenerator.verifyToken(token);
    assertEquals("user_id_test", decodedJWT.getSubject());
    assertEquals("role_test", decodedJWT.getClaim("role").asString());
  }
}
