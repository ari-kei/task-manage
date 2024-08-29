package com.arikei.auth.interfaces.authenticate;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.arikei.auth.domains.Authenticator;
import com.arikei.auth.domains.JwtGenerator;
import com.arikei.auth.domains.PasswordEncoder;
import com.arikei.auth.domains.entities.LoginInfo;
import com.arikei.auth.domains.repositoryif.JwtKeyRepositoryIF;
import com.arikei.auth.domains.repositoryif.UserRepositoryIF;
import com.arikei.auth.interfaces.authenticate.request.LoginRequest;
import com.arikei.auth.interfaces.authenticate.response.LoginResponse;

@RequestMapping("")
@Controller
public class LoginController {

  private UserRepositoryIF userRepositoryIF;
  private JwtKeyRepositoryIF jwtKeyRepositoryIF;
  private PasswordEncoder passwordEncoder;

  public LoginController(UserRepositoryIF urIf, JwtKeyRepositoryIF jrIf, PasswordEncoder pe) {
    this.userRepositoryIF = urIf;
    this.jwtKeyRepositoryIF = jrIf;
    this.passwordEncoder = pe;
  }

  @PostMapping("login")
  // TODO 返却する型は再考する必要があるかも(APIの定義から)
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    // リクエストの詰め替え
    LoginInfo loginInfo = new LoginInfo(request.getUserId(), request.getPassword());

    Authenticator authenticator = new Authenticator(this.userRepositoryIF, this.passwordEncoder,
        new JwtGenerator(jwtKeyRepositoryIF));
    String jwt = "";
    try {
      jwt = authenticator.authenticate(loginInfo);
    } catch (IllegalArgumentException e) {
      // TODO 引数不正の場合の対応
      return ResponseEntity.status(HttpStatusCode.valueOf(401))
          .body(new LoginResponse("", "unauthrized"));
    }
    return ResponseEntity.ok().body(new LoginResponse(jwt, ""));
  }

}
