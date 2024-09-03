package com.arikei.auth.interfaces.authenticate.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
  private String accessToken;
  private String error;
}
