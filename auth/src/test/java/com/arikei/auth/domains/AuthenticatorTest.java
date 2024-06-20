package com.arikei.auth.domains;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.arikei.auth.domains.entities.LoginInfo;
import com.arikei.auth.domains.entities.User;
import com.arikei.auth.domains.repositoryif.JwtKeyRepositoryIF;
import com.arikei.auth.domains.repositoryif.UserRepositoryIF;

public class AuthenticatorTest {
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
  void Authenticate_InvalidUserId_ThrowIllegalArgumentException() {
    // 準備
    class UserRepositoryMock implements UserRepositoryIF {
      public Optional<User> findById(String userId) {
        return Optional.empty();
      }
    }
    LoginInfo loginInfo = new LoginInfo("userId", "password");
    Authenticator authenticator = new Authenticator(new UserRepositoryMock(), new PasswordEncoder(),
        new JwtGenerator(new JwtKeyRepositoryMock()));

    // 実行&検証
    assertThrows(IllegalArgumentException.class, () -> authenticator.authenticate(loginInfo));
  }

  @Test
  void Authenticate_InvalidPassword_ThrowIllegalArgumentExcepetion() {
    // 準備
    class UserRepositoryMock implements UserRepositoryIF {
      public Optional<User> findById(String userId) {
        User user = new User("userid", "name", "password", "role");
        return Optional.of(user);
      }
    }
    LoginInfo loginInfo = new LoginInfo("userId", "password");
    Authenticator authenticator = new Authenticator(new UserRepositoryMock(), new PasswordEncoder(),
        new JwtGenerator(new JwtKeyRepositoryMock()));

    // 実行&検証
    assertThrows(IllegalArgumentException.class, () -> authenticator.authenticate(loginInfo));
  }

  @Test
  void Authentiate_Ok_ReturnJWT() {
    // 準備
    PasswordEncoder passwordEncoder = new PasswordEncoder();
    String hasedPassword = passwordEncoder.getPasswordEncoder().encode("password");
    class UserRepositoryMock implements UserRepositoryIF {
      public Optional<User> findById(String userId) {
        User user = new User("userId", "name", hasedPassword, "role");
        return Optional.of(user);
      }
    }
    JwtGenerator jwtGenerator = new JwtGenerator(new JwtKeyRepositoryMock());

    LoginInfo loginInfo = new LoginInfo("userId", "password");
    Authenticator authenticator =
        new Authenticator(new UserRepositoryMock(), passwordEncoder, jwtGenerator);

    // 実行
    String result = authenticator.authenticate(loginInfo);

    // 検証
    // 作成されるJWTは有効期限が作成時刻を基に設定されるため全く同じものを違うタイミングで作成できない
    // JWTの作成自体はJwtGeneratorのユニットテストで担保しているためここではnullが返らないことを確認する
    assertNotNull(result);
  }
}
