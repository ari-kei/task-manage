package com.arikei.auth.domains;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.arikei.auth.domains.entities.AuthInfo;
import com.arikei.auth.domains.entities.User;
import com.arikei.auth.domains.entities.VerifyInfo;
import com.arikei.auth.domains.repositoryif.JwtKeyRepositoryIF;
import com.arikei.auth.domains.repositoryif.UserRepositoryIF;

public class VerifierTest {
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
  void Verify_InvalidJWT__ThrowIllegalArgumentException() {
    // 準備
    PasswordEncoder passwordEncoder = new PasswordEncoder();
    String hasedPassword = passwordEncoder.getPasswordEncoder().encode("password");
    User user = new User("userId", "name", hasedPassword, "role");
    class UserRepositoryMock implements UserRepositoryIF {
      public Optional<User> findById(String userId) {
        return Optional.of(user);
      }
    }
    JwtGenerator jwtGenerator = new JwtGenerator(new JwtKeyRepositoryMock());
    Verifier verifier = new Verifier(jwtGenerator, new UserRepositoryMock());

    VerifyInfo verifyInfo = new VerifyInfo("invalidJWT");

    // 実行&検証
    assertThrows(IllegalArgumentException.class, () -> verifier.verify(verifyInfo));
  }

  @Test
  void Verify_NoUser__ThrowIllegalArgumentException() {
    class UserRepositoryMock implements UserRepositoryIF {
      public Optional<User> findById(String userId) {
        return Optional.empty();
      }
    }
    JwtGenerator jwtGenerator = new JwtGenerator(new JwtKeyRepositoryMock());
    Verifier verifier = new Verifier(jwtGenerator, new UserRepositoryMock());

    VerifyInfo verifyInfo = new VerifyInfo(jwtGenerator.generateJwt(new AuthInfo("userid")));

    assertThrows(IllegalArgumentException.class, () -> verifier.verify(verifyInfo));
  }

  @Test
  void Verify_Ok_ReturnUser() {
    // 準備
    PasswordEncoder passwordEncoder = new PasswordEncoder();
    String hasedPassword = passwordEncoder.getPasswordEncoder().encode("password");
    User user = new User("userId", "name", hasedPassword, "role");
    class UserRepositoryMock implements UserRepositoryIF {
      public Optional<User> findById(String userId) {
        return Optional.of(user);
      }
    }
    JwtGenerator jwtGenerator = new JwtGenerator(new JwtKeyRepositoryMock());
    Verifier verifier = new Verifier(jwtGenerator, new UserRepositoryMock());

    VerifyInfo verifyInfo = new VerifyInfo(jwtGenerator.generateJwt(new AuthInfo("userid")));

    // 実行
    User actual = verifier.verify(verifyInfo);

    // 検証
    assertEquals(user, actual);
  }
}
